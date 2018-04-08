package utils.time;

import ppa.config.injector.InjectLog;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ppa.model.observation.ReceiverDataModel;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Scope("prototype")
public class Sections {

    private ReceiverDataModel.ObservationMode mode;

    @InjectLog
    private Logger log;

    private int edgePoints;
    private int delT;
    private int hole;
    private int sect;
    private RealMatrix matrix;
    private int svNum;
    private int epochNum;

    private List<Integer> tn = new LinkedList<>();
    private List<Integer> tk = new LinkedList<>();
    private List<Integer> NSect = new LinkedList<>();
    private List<Integer> time = Collections.emptyList();

    private Sections() {}

    public Sections(List<Integer> time, RealMatrix matrix, ReceiverDataModel.ObservationMode mode, int delT) {
        this.matrix = matrix;
        this.mode = mode;
        this.time = time;
        this.delT = delT;
        init();
    }

    public Sections(List<Integer> time, RealMatrix matrix, ReceiverDataModel.ObservationMode mode) {
        this(time, matrix, mode, 1);
    }

    public Sections(RealMatrix matrix, ReceiverDataModel.ObservationMode mode) {
        this(null, matrix, mode);
    }

    public Sections(double[][] data, ReceiverDataModel.ObservationMode mode) {
        this(null, new Array2DRowRealMatrix(data), mode);
    }

    public Sections(List<Integer> time, double[][] data, ReceiverDataModel.ObservationMode mode) {
        this(time, new Array2DRowRealMatrix(data), mode);
    }

    public SectionsData findSections() {
        try {
            for (int sv = 0; sv < svNum; sv++) {
                double[] vector = matrix.getColumn(sv);

                List<Integer> indexP = Filters.find(vector);
                if (CollectionUtils.isNotEmpty(indexP)) {
                    double[] diffP = Filters.diff(indexP);
                    List<Integer> sleepP = Filters.findGreaterThan(diffP, hole);

                    List<Integer> pfp = new CopyOnWriteArrayList<>();
                    List<Integer> zfp = new CopyOnWriteArrayList<>();

                    pfp.add(indexP.get(0));
                    for (Integer index : sleepP) {
                        pfp.add(indexP.get(index + 1));
                        zfp.add(indexP.get(index));
                    }
                    zfp.add(indexP.get(indexP.size() - 1));

                    List<Integer> lTime = TimeUtils.intervalBetween(zfp, pfp);
                    List<Integer> indexesToRemove = Filters.findLessThan(lTime, sect);

                    for (Integer indexToRemove : indexesToRemove) {
                        pfp.remove(pfp.get(indexToRemove));
                        pfp.remove(zfp.get(indexToRemove));
                    }

                    for (int i = 0; i < pfp.size(); i++) {
                        indexP = Filters.find(ArrayUtils.subarray(vector, pfp.get(i), zfp.get(i) + 1));
                        if (indexP.isEmpty()) {
                            continue;
                        }
                        diffP = Filters.diff(indexP);
                        sleepP = Filters.findGreaterThan(diffP, 1);

                        List<Integer> time = TimeUtils.createTimeVector(pfp.get(i), zfp.get(i));

                        List<Integer> pf = new CopyOnWriteArrayList<>();
                        List<Integer> zf = new CopyOnWriteArrayList<>();

                        pf.add(indexP.get(0));
                        for (Integer index : sleepP) {
                            pf.add(indexP.get(index + 1));
                            zf.add(indexP.get(index));
                        }
                        zf.add(indexP.get(indexP.size() - 1));

                        lTime = TimeUtils.intervalBetween(zf, pf);

                        indexesToRemove = Filters.findLessThan(lTime, edgePoints);

                        for (Integer indexToRemove : indexesToRemove) {
                            pf.remove(pf.get(indexToRemove));
                            zf.remove(zf.get(indexToRemove));
                        }

                        if (CollectionUtils.isNotEmpty(pf)) {
                            pfp.set(i, time.get(pf.get(0)));
                            zfp.set(i, time.get(zf.get(zf.size() - 1)));

                            int length = zfp.get(i) - pfp.get(i) + 1;
                            int countNonZeroElements = Filters.find(ArrayUtils.subarray(vector, pfp.get(i), zfp.get(i) + 1)).size();

                            double percentageOfNonZeroValues = Double.valueOf(countNonZeroElements) / length;

                            if (percentageOfNonZeroValues > 2.0d / 3) {
                                tn.add(this.time.get(pfp.get(i)));
                                tk.add(this.time.get(zfp.get(i)));
                                NSect.add(sv);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (int i = 0; i < NSect.size(); i++) {
                String msg = "SV " + NSect.get(i) + " From " + tn.get(i) + " To " + tk.get(i);
                if (log != null) {
                    log.info(msg);
                } else {
                    System.out.println(msg);
                }
            }
        }
        return getSections();
    }

    private void init() {
        if (matrix == null) {
            throw new NullPointerException("Can't find section due to null data");
        } else {
            svNum = matrix.getColumnDimension();
            epochNum = matrix.getRowDimension();
        }
        if (mode == null) {
            mode = ReceiverDataModel.ObservationMode.KINEMATIC_MODE;
        }
        if (edgePoints == 0) {
            edgePoints = mode.getEdgePoints();
        }
        if (delT == 0) {
            delT = mode.getDelT();
        }
        if (hole == 0) {
            hole = mode.getGap();
        }
        if (sect == 0) {
            sect = mode.getSect();
        }
        if (CollectionUtils.isEmpty(time)) {
            time = TimeUtils.createTimeVector(0, epochNum - 1);
        }
    }

    public SectionsData getSections() {
        return new SectionsData(tn, tk, NSect);
    }

    public static @Data class SectionsData {
        List<Integer> tn;
        List<Integer> tk;
        List<Integer> NSect;

        SectionsData(List<Integer> tn, List<Integer> tk, List<Integer> NSect) {
            this.tn = tn;
            this.tk = tk;
            this.NSect = NSect;
        }
    }

    public static void main(String[] args) {
        double[][] testArray = {
                {0, 1, 1, 1, 0, 1, 1, 0, 0, 0},
                {1, 1, 1, 1, 0, 1, 1, 1, 0, 0},
                {1, 1, 0, 0, 0, 1, 1, 1, 1, 0},
                {1, 0, 1, 1, 0, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
                {0, 0, 1, 0, 0, 1, 1, 1, 1, 0},
                {0, 0, 1, 0, 0, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 0}
        };
        Sections sections = new Sections(testArray, ReceiverDataModel.ObservationMode.TEST_MODE);
        SectionsData sectionsData = sections.findSections();
        System.out.println(sectionsData);
    }
}

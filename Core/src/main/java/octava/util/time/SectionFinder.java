package octava.util.time;

import octava.model.observation.ReceiverDataModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.isNull;

public class SectionFinder {

    private ReceiverDataModel.ObservationMode mode;

    private int edgePoints;
    private int delT;
    private int hole;
    private int sect;
    private int svNum;
    private int epochNum;

    private RealMatrix matrix;

    private List<Integer> tn = new LinkedList<>();
    private List<Integer> tk = new LinkedList<>();
    private List<Integer> NSect = new LinkedList<>();
    private List<Integer> time = Collections.emptyList();

    public SectionFinder(List<Integer> time, RealMatrix matrix, ReceiverDataModel.ObservationMode mode, int delT) {
        this.matrix = matrix;
        this.mode = isNull(mode) ? ReceiverDataModel.ObservationMode.KINEMATIC_MODE : mode;
        this.time = time;
        this.delT = delT;
        init();
    }

    public SectionFinder(List<Integer> time, RealMatrix matrix, ReceiverDataModel.ObservationMode mode) {
        this(time, matrix, mode, 1);
    }

    public SectionFinder(RealMatrix matrix, ReceiverDataModel.ObservationMode mode) {
        this(null, matrix, mode);
    }

    public SectionFinder(double[][] data, ReceiverDataModel.ObservationMode mode) {
        this(null, new Array2DRowRealMatrix(data), mode);
    }

    public SectionFinder(List<Integer> time, double[][] data, ReceiverDataModel.ObservationMode mode) {
        this(time, new Array2DRowRealMatrix(data), mode);
    }

    public SectionsData find() {
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

    public static class SectionsData {

        List<Integer> tn;
        List<Integer> tk;
        List<Integer> NSect;

        SectionsData(List<Integer> tn, List<Integer> tk, List<Integer> NSect) {
            this.tn = tn;
            this.tk = tk;
            this.NSect = NSect;
        }

        public List<Integer> getTn() {
            return tn;
        }

        public List<Integer> getTk() {
            return tk;
        }

        public List<Integer> getNSect() {
            return NSect;
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
        SectionFinder sectionService = new SectionFinder(testArray, ReceiverDataModel.ObservationMode.TEST_MODE);
        SectionsData sectionsData = sectionService.find();
        System.out.println(sectionsData);
    }
}

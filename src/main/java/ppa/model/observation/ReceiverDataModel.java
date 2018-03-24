package ppa.model.observation;

import com.google.common.primitives.Ints;
import config.AppInitializer;
import config.HibernateConfiguration;
import config.MvcConfiguration;
import config.injector.LogInjector;
import lombok.Data;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ppa.dto.EpochDto;
import ppa.model.observation.header.impl.*;
import ppa.model.rinex.Gnss;
import ppa.model.rinex.Observations;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Scope("prototype")
public @Data class ReceiverDataModel implements Gnss {

    public static final ReceiverDataModel NULL = new NullReceiverDataModel();

    private ObservationMode observationMode;

    private RinexVersionType rinexVersionType;

    private PgmRunByDate pgmRunByDate;

    private MarkerName markerName;

    private ObserverAgency observerAgency;

    private RecTypeVers recTypeVers;

    private AntType antType;

    private ApproxPos approxPos;

    private AntennaDelta antennaDelta;

    private WaveLengthFact wavelengthFact;

    private TypesOfObs typesOfObs;

    private LeapSeconds leapSeconds;

    private Map<ObsType, RealMatrix> obs = new LinkedHashMap<>();

    private List<Integer> seconds = new LinkedList<>();

    private List<LocalDateTime> time = new LinkedList<>();

    private Map<ObsType, Observations> rawObs = new LinkedHashMap<>();

    private Set<Double> epoch;

    private int numberOfObsTypes;

    public void buildObsMatrixFromRawData(){

        if (rawObs == null) {
            return;
        }

        for (Map.Entry<ObsType, Observations> obsByType : rawObs.entrySet()) {

            Observations rawEpoches = obsByType.getValue();

            if (rawEpoches == null || rawEpoches.getObs() == null) {
                continue;
            }

            RealMatrix matrix = new Array2DRowRealMatrix(rawEpoches.size(), Gnss.MAX_SAT);

            int epochCounter = 0;
            for (Map.Entry<LocalDateTime, RealMatrix> rawEpoch : rawEpoches.getObs().entrySet()) {
                matrix.setRowMatrix(epochCounter, rawEpoch.getValue());
                epochCounter++;
            }

            obs.put(obsByType.getKey(), matrix);
        }

    }

    public void addObservations(EpochDto epoch) {
        if (epoch == null) {
            return;
        }

        for (String svCode : epoch.getSatellites()) {

            LocalDateTime epochTime = epoch.getEpochTime();
            int satellite = Ints.tryParse(svCode.substring(1,3));

            for (int index = 0; index < typesOfObs.size(); index++) {
                ObsType type = typesOfObs.get(index);
                Observations observations = rawObs.get(type);

                if (observations == null) {
                    observations = new Observations(type);
                    rawObs.put(type, observations);
                }

                RealMatrix epochArray = observations.getEpoch(epochTime);

                if (epochArray == null) {
                    epochArray = new Array2DRowRealMatrix(1, Gnss.MAX_SAT);
                    observations.putEpoch(epochTime, epochArray);
                }

                double obsValue = epoch.getEpochData(index, svCode);
                epochArray.setEntry(0, satellite, obsValue);

                observations.putEpoch(epochTime, epochArray);
                observations.putFlag(epochTime, epoch.getFlag());
            }
        }
    }

    public int getNumberOfObsTypes() {
        return (numberOfObsTypes == 0) ? numberOfObsTypes = typesOfObs.size() : numberOfObsTypes;
    }

    private static class NullReceiverDataModel extends ReceiverDataModel {
        @Override
        public String toString() {
            return "NullReceiverDataModel";
        }
    }

    public enum ObservationMode {
        STATIC_MODE(1, 10, 50, 180), KINEMATIC_MODE(1, 20, 5, 180), STOP_AND_GO(0, 0, 0, 0), TEST_MODE(1, 3, 2, 5);

        private int delT;
        private int edgePoints;
        private int hole;
        private int sect;

        ObservationMode(int delT, int edgePoints, int hole, int sect) {
            this.delT = delT;
            this.edgePoints = edgePoints;
            this.hole = hole;
            this.sect = sect;
        }

        public int getEdgePoints() {
            return edgePoints;
        }

        public int getDelT() {
            return delT;
        }

        public int getHole() {
            return hole;
        }

        public int getSect() {
            return sect;
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppInitializer.class);
        context.register(MvcConfiguration.class);
        context.register(HibernateConfiguration.class);
        context.register(BeanFactory.class);
        context.registerBean(LogInjector.class);
        context.scan("ppa", "utils", "config");

        ReceiverDataModel receiverDataModel1 = context.getBean(ReceiverDataModel.class);
        ReceiverDataModel receiverDataModel2 = context.getBean(ReceiverDataModel.class);

        Assert.isTrue(receiverDataModel1 != receiverDataModel2, "GOOD");
    }
}

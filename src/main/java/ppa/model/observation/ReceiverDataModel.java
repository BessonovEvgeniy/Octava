package ppa.model.observation;

import business.config.BusinessHibernateConfig;
import com.google.common.primitives.Ints;
import config.AppInitializer;
import config.MvcConfiguration;
import lombok.Data;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ppa.config.injector.LogInjector;
import ppa.dto.EpochDto;
import ppa.model.observation.header.impl.*;
import ppa.model.rinex.Gnss;
import ppa.model.rinex.Observations;
import utils.time.Sections;

import java.time.LocalDateTime;
import java.util.*;

@Component("receiverDataModel")
@Scope("prototype")
public @Data
class ReceiverDataModel implements Gnss {

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

    private List<Integer> seconds = new LinkedList<>();

    private List<LocalDateTime> time = new LinkedList<>();

    private Map<ObsType, Observations> obs = new LinkedHashMap<>();

    private Set<Double> epoch;

    private Sections.SectionsData sections;

    private int numberOfObsTypes;

    public void addObservations(EpochDto epoch) {
        if (epoch == null) {
            return;
        }
        LocalDateTime epochTime = epoch.getEpochTime();
        if (!time.contains(epochTime)) {
            time.add(epochTime);
        }
        for (String svCode : epoch.getSatellites()) {
            int satellite = Ints.tryParse(svCode.substring(1, 3));

            for (int index = 0; index < typesOfObs.size(); index++) {

                ObsType type = typesOfObs.get(index);
                Observations observations = obs.get(type);

                if (observations == null) {
                    observations = new Observations(type);
                    obs.put(type, observations);
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

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(antennaDelta.toString()).add(antType.toString()).add(approxPos.toString()).add(pgmRunByDate.toString()).
                add(rinexVersionType.toString());
        return joiner.toString();
    }

    public int getEpochSize() {
        return time.size();
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

    public Observations getObsByType(ObsType obsType) {
        return obs.get(obsType);
    }

    public enum ObservationMode {
        STATIC_MODE(1, 10, 50, 180), KINEMATIC_MODE(1, 20, 5, 180), STOP_AND_GO(0, 0, 0, 0), TEST_MODE(1, 3, 2, 5);

        private int delT;
        private int edgePoints;
        private int gap;
        private int sect;

        ObservationMode(int delT, int edgePoints, int gap, int sect) {
            this.delT = delT;
            this.edgePoints = edgePoints;
            this.gap = gap;
            this.sect = sect;
        }

        public int getEdgePoints() {
            return edgePoints;
        }

        public int getDelT() {
            return delT;
        }

        public int getGap() {
            return gap;
        }

        public int getSect() {
            return sect;
        }

        @Override
        public String toString() {
            return "Observation mode " + this.name();
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppInitializer.class);
        context.scan("ppa");
        context.register(MvcConfiguration.class);
        context.register(BusinessHibernateConfig.class);
        context.register(BeanFactory.class);
        context.registerBean(LogInjector.class);
//        ReceiverDataModel rdm = context.getBean(ReceiverDataModel.class);
//
//        Assert.isTrue(receiverDataModel1 != receiverDataModel2, "GOOD");
    }
}

package ppa.model.observation;

import com.google.common.primitives.Ints;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ppa.dto.EpochDto;
import ppa.model.observation.header.impl.*;
import ppa.model.rinex.Gnss;
import ppa.model.rinex.Observations;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Scope("prototype")
public @Data class ReceiverDataModel implements Gnss {

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

    private Map<ObsType, Double[][]> obs = new LinkedHashMap<>();

    private List<LocalDateTime> time = new LinkedList<>();

    private Map<ObsType, Observations> rawObs = new LinkedHashMap<>();

    private Set<Double> epoch;

    public void addObservations(EpochDto epoch) {

        for (String svCode : epoch.getSatellites()) {

            int satellite = Ints.tryParse(svCode.substring(1,3));

            for (int index = 0; index < typesOfObs.size(); index++) {
                ObsType type = typesOfObs.get(index);
                double[] epochArray = getObservations(type, epoch.getEpochTime());
                double obsValue = epoch.getEpochData(svCode, index);

                if (epochArray[satellite] != 0) {
                    System.out.println("Epoch " + epoch.getEpochTime() + " value " + epochArray[satellite] + " will be replaced by " + obsValue);
                }
                epochArray[satellite] = obsValue;
                updateEpochData(type, epoch.getEpochTime(), epochArray);
                updateEpochFlag(type, epoch.getEpochTime(), epoch.getFlag());
            }
        }
    }

    private void updateEpochData(ObsType type, LocalDateTime epochTime, double[] epochArray) {
        Observations observations = rawObs.get(type);
        observations.upsertEpoch(epochTime, epochArray);
    }

    private void updateEpochFlag(ObsType type, LocalDateTime epochTime, int flag) {
        Observations observations = rawObs.get(type);
        observations.upsertFlag(epochTime, flag);
    }

    public Observations getObservations(ObsType type) {
        Observations observations = rawObs.get(type);

        if (observations == null) {
            observations = new Observations(type);
            rawObs.put(type, observations);
        }

        return observations;
    }

    public double[] getObservations(ObsType type, LocalDateTime epochTime) {
        Observations observations = getObservations(type);
        double[] epochArray = observations.getEpoch(epochTime);

        if (epochArray == null) {
            epochArray = new double[Gnss.MAX_SAT];
            observations.upsertEpoch(epochTime, epochArray);
        }

        return epochArray;
    }

    public static final ReceiverDataModel NULL = new NullReceiverDataModel();

    private static class NullReceiverDataModel extends ReceiverDataModel {

        @Override
        public String toString() {
            return "NullReceiverDataModel";
        }
    }
}

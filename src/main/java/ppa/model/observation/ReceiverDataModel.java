package ppa.model.observation;

import Jama.Matrix;
import com.google.common.primitives.Ints;
import config.injector.InjectLog;
import lombok.Data;
import org.slf4j.Logger;
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

    @InjectLog
    private Logger logger;

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

    private Map<ObsType, Matrix> obs = new LinkedHashMap<>();

    private List<Integer> seconds = new LinkedList<>();

    private List<LocalDateTime> time = new LinkedList<>();

    private Map<ObsType, Observations> rawObs = new LinkedHashMap<>();

    private Set<Double> epoch;

    public void buildObsMatrixFromRawData(){

        for (Map.Entry<ObsType, Observations> obsByType : rawObs.entrySet()) {

            Observations rawEpoches = obsByType.getValue();
            int epochNum = rawEpoches.size();
            Matrix matrix = new Matrix(epochNum, Gnss.MAX_SAT, 0);

            int epochCounter = 0;
            for (Map.Entry<LocalDateTime, Matrix> rawEpoch : rawEpoches.getObs().entrySet()) {

                matrix.setMatrix(epochCounter,epochCounter,0, Gnss.MAX_SAT-1, rawEpoch.getValue());
                epochCounter++;
            }

            obs.put(obsByType.getKey(), matrix);
        }

    }

    public void addObservations(EpochDto epoch) {

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

                Matrix epochArray = observations.getEpoch(epochTime);

                if (epochArray == null) {
                    epochArray = new Matrix(1, Gnss.MAX_SAT, 0);
                    observations.upsertEpoch(epochTime, epochArray);
                }

                double obsValue = epoch.getEpochData(index, svCode);
                double value = epochArray.get(0, satellite);
                if (value != 0 && value != obsValue) {
                    logger.debug("Epoch " + epoch.getEpochTime() + " value " + value + " will be replaced by " + obsValue);
                }
                epochArray.set(0, satellite, obsValue);

                observations.upsertEpoch(epochTime, epochArray);
                observations.upsertFlag(epochTime, epoch.getFlag());
            }
        }
    }

    public static final ReceiverDataModel NULL = new NullReceiverDataModel();

    private static class NullReceiverDataModel extends ReceiverDataModel {

        @Override
        public String toString() {
            return "NullReceiverDataModel";
        }
    }
}

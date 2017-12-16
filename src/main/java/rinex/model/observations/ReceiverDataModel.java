package rinex.model.observations;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import rinex.model.observations.header.impl.*;
import rinex.model.rinex.Gnss;
import rinex.model.rinex.Observations;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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

    private WavelengthFact wavelengthFact;

    private TypesOfObs typesOfObs;

    private LeapSeconds leapSeconds;

    private Map<ObsType, Observations> obs = new LinkedHashMap<>();

    private Set<Double> epoch;

    public static final ReceiverDataModel NULL = new NullReceiverDataModel();

    private static class NullReceiverDataModel extends ReceiverDataModel {

        @Override
        public String toString() {
            return "NullReceiverDataModel";
        }
    }
}

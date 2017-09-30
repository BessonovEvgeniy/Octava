package rinex.model.observations;

import lombok.Data;
import rinex.model.rinex.GLONASS;
import rinex.model.rinex.GPS;
import rinex.model.rinex.Observations;
import rinex.service.Impl.observations.rinex.rinexImpl.header.*;

import java.util.Map;
import java.util.Set;

public @Data class ReceiverDataModel implements GPS, GLONASS {

    public enum GNSS {
        GPS, GLONASS
    }

    private RinexVersionType rinexVersionType;

    private PgmRunByDate pgmRunByDate;

    private MarkerName markerName;

    private ObserverAgency observerAgency;

    private RecTypeVers recTypeVers;

    private AntType antType;

    private ApproxPos approxPos;

    private AntennaDelta antennaDelta;

    private WavelengthFact wavelengthFact;

    private TypesOfObserv typesOfObserv;

    private LeapSeconds leapSeconds;

    private Map<GNSS, Set<Observations>> obs;

    private Set<Double> epoch;

    public static final ReceiverDataModel NULL = new NullReceiverDataModel();

    private static class NullReceiverDataModel extends ReceiverDataModel {

        @Override
        public String toString() {
            return "NullReceiverDataModel";
        }
    }
}

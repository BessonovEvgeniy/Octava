package rinex.model.observations;

import lombok.Data;
import rinex.model.rinex.GLONASS;
import rinex.model.rinex.GPS;
import rinex.model.rinex.Observations;
import rinex.service.Impl.observations.rinex.rinexImpl.header.PgmRunByDate;
import rinex.service.Impl.observations.rinex.rinexImpl.header.RinexVersionType;
import rinex.service.Impl.observations.rinex.rinexImpl.header.MarkerName;
import rinex.service.Impl.observations.rinex.rinexImpl.header.ObserverAgency;

import java.util.Map;
import java.util.Set;

public @Data class ReceiverDataModel implements GPS, GLONASS {

    private enum GNSS {
        GPS, GLONASS
    }

    private RinexVersionType rinexVersionType;

    private PgmRunByDate pgmRunByDate;

    private MarkerName markerName;

    private ObserverAgency observerAgency;

    private Map<GNSS, Set<Observations>> obs;

    private Set<Double> epoch;

    public static final ReceiverDataModel NULL = new NullReceiverDataModel();

    private static class NullReceiverDataModel extends ReceiverDataModel {

        @Override
        public String toString() {
            return "Null TNP";
        }
    }
}

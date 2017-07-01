package model.observations;

import lombok.Data;
import model.rinex.*;
import service.Impl.observations.rinex.rinexImpl.header.MarkerName;
import service.Impl.observations.rinex.rinexImpl.header.ObserverAgency;
import service.Impl.observations.rinex.rinexImpl.header.PgmRunByDate;
import service.Impl.observations.rinex.rinexImpl.header.RinexVersionType;

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
}

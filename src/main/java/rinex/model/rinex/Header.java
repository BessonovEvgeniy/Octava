package rinex.model.rinex;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import rinex.service.Impl.observations.rinex.rinexImpl.header.*;

public @Data class Header  {

    @Autowired
    private RinexVersionType rinexVersionType;

    @Autowired
    private PgmRunByDate pgmRunByDate;

    @Autowired
    private MarkerNumber markerName;

    @Autowired
    private ObserverAgency observerAgency;

    @Autowired
    private RecTypeVers recTypeVers;

    @Autowired
    private AntType antType;

    @Autowired
    private ApproxPos approxPos;

    @Autowired
    private AntennaDelta antennaDelta;

    @Autowired
    private WavelengthFact wavelengthFact;

    @Autowired
    private TypesOfObserv typesOfObserv;

    public static class HeaderNull extends Header {
        public String toString() {
            return "Null Header";
        }
    }

}
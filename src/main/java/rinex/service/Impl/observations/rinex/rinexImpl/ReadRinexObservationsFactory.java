package rinex.service.Impl.observations.rinex.rinexImpl;

import rinex.service.State;

import java.util.HashMap;
import java.util.Map;

class ReadRinexObservationsFactory {

    static Map<String, State> rinexObservationReaders = new HashMap<String, State>();

    static {
        rinexObservationReaders.put("2.11", new ReadRinexObservationsV211Impl());
    }
}
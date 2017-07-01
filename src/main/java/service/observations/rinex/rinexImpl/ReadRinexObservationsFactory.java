package service.observations.rinex.rinexImpl;

import service.observations.rinex.State;

import java.util.HashMap;
import java.util.Map;

class ReadRinexObservationsFactory {

    static Map<String, State> rinexObservationReaders = new HashMap<String, State>();

    static {
        rinexObservationReaders.put("211", new ReadRinexObservationsV211Impl());
    }

    public State getRinexObservationReader(String version) {
        return rinexObservationReaders.get(version);
    }

}
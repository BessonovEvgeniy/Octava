package rinex.service.impl.observations.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rinex.model.observations.ReceiverDataModel;
import rinex.service.State;

import java.util.HashMap;
import java.util.Map;

@Service
class ReadRinexObservationsFactory {

    @Autowired
    private Map<String, State> rinexObservationReaders;

    public State getReadRinexObservationsReader(String version) throws Exception {

        State obsReader = rinexObservationReaders.get(version);
        if (obsReader == null) {
            throw new Exception();
        }

        return obsReader;
    }
}
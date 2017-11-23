package rinex.service.impl.observations.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rinex.exception.UnknownObservationProcessorVersionException;
import rinex.service.State;

import java.util.Map;

@Service
class ReadRinexObservationsFactory {

    @Autowired
    private Map<String, AbstractReadRinexObservations> readers;

    public State getReadRinexObservationsReader(String version) throws Exception {

        State obsReader = readers.get(version);
        if (obsReader == null) {
            throw new UnknownObservationProcessorVersionException(version);
        }

        return obsReader;
    }
}
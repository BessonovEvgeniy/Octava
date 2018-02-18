package ppa.service.impl.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppa.exception.UnknownObservationProcessorVersionException;
import ppa.service.State;

import java.util.Map;

@Service
public class ReadRinexObservationsFactory {

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
package ppa.service.impl.rinex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppa.exception.UnknownObservationProcessorVersionException;
import ppa.service.RinexReader;

import java.util.Map;

@Service
public class ReadRinexObservationsFactory {

    @Autowired
    private Map<String, AbstractReadRinexObservations> readers;

    public RinexReader getReadRinexObservationsReader(String version) throws Exception {

        RinexReader obsReader = readers.get(version);
        if (obsReader == null) {
            throw new UnknownObservationProcessorVersionException(version);
        }

        return obsReader;
    }
}
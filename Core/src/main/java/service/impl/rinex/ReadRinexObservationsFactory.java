package service.impl.rinex;

import exception.UnknownObservationProcessorVersionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RinexReader;
import service.RinexSectionReader;

import java.util.Map;

@Service
public class ReadRinexObservationsFactory {

    @Autowired
    private Map<String, RinexSectionReader> readers;

    public RinexSectionReader getReadRinexObservationsReader(String version) {

        RinexSectionReader obsReader = readers.get(version);
        if (obsReader == null) {
            throw new UnknownObservationProcessorVersionException(version);
        }

        return obsReader;
    }
}
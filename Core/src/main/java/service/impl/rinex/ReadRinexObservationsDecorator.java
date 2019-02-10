package service.impl.rinex;

import model.observation.ReceiverDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.RinexReader;
import service.RinexSectionReader;

import java.io.BufferedReader;
import java.io.InputStream;

@Service
public class ReadRinexObservationsDecorator implements RinexSectionReader {

    private static final Logger LOG = LoggerFactory.getLogger(ReadRinexObservationsDecorator.class);

    @Autowired
    private ReadRinexObservationsFactory obsFactory;

    public void read(BufferedReader reader, ReceiverDataModel data) {

        String rinexVersion = data.getRinexVersionType().getVersion();

        RinexSectionReader rinexObservationReader = obsFactory.getReadRinexObservationsReader(rinexVersion);
        LOG.info("Perform processing of RINEX file version : " + rinexVersion);
        LOG.info("Observation reading...");
        rinexObservationReader.read(reader, data);
        LOG.info("Done.");
    }
}
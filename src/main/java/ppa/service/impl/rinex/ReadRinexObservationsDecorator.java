package ppa.service.impl.rinex;

import config.injector.InjectLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppa.model.observation.ReceiverDataModel;
import ppa.service.RinexReader;

import java.io.BufferedReader;

@Service
public class ReadRinexObservationsDecorator implements RinexReader {

    @Autowired
    private ReadRinexObservationsFactory obsFactory;

    @InjectLog
    private Logger log;

    public void read(BufferedReader reader, ReceiverDataModel data) throws Exception {

        String rinexVersion = data.getRinexVersionType().getVersion();

        RinexReader rinexObservationReader = obsFactory.getReadRinexObservationsReader(rinexVersion);
        log.info("Perform processing of RINEX file version : " + rinexVersion);
        log.info("Observation reading...");
        rinexObservationReader.read(reader, data);
        log.info("Done.");
    }
}
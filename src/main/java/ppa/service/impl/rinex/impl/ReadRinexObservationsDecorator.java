package ppa.service.impl.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppa.model.observation.ReceiverDataModel;
import ppa.service.State;

import java.io.BufferedReader;

@Service
public class ReadRinexObservationsDecorator implements State {

    @Autowired
    private ReadRinexObservationsFactory obsFactory;

    public void read(BufferedReader reader, ReceiverDataModel data) throws Exception {

        String rinexVersion = data.getRinexVersionType().getVersion();

        State rinexObservationReader = obsFactory.getReadRinexObservationsReader(rinexVersion);

        rinexObservationReader.read(reader, data);
    }
}
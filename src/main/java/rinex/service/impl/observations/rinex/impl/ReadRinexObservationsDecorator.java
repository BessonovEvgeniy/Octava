package rinex.service.impl.observations.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rinex.model.observations.ReceiverDataModel;
import rinex.service.State;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;

@Service
class ReadRinexObservationsDecorator implements State {

    @Autowired
    ReadRinexObservationsFactory obsFactory;

    public void read(BufferedReader reader, @NotNull ReceiverDataModel data) throws Exception {

        String rinexVersion = data.getRinexVersionType().getVersion();

        State rinexObservationReader = obsFactory.getReadRinexObservationsReader(rinexVersion);

        rinexObservationReader.read(reader, data);
    }
}
package rinex.service.impl.observations.rinex.impl;

import org.springframework.stereotype.Service;
import rinex.model.observations.ReceiverDataModel;
import rinex.service.State;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;

@Service
class ReadRinexObservationsDecorator implements State {

    public void read(BufferedReader reader, @NotNull ReceiverDataModel data) throws Exception {
        String rinexVersion = data.getRinexVersionType().getVersion();
        if (rinexVersion != null) {
            State rinexObservationReader = new ReadRinexObservationsFactory(data).
                    getReadRinexObservationsReader(rinexVersion);
            if (rinexObservationReader != null) {
                rinexObservationReader.read(reader, data);
            }
        }
    }
}
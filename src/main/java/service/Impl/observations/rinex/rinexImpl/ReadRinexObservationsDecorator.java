package service.Impl.observations.rinex.rinexImpl;

import model.observations.ReceiverDataModel;
import service.State;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;

class ReadRinexObservationsDecorator implements State {

    public void read(BufferedReader reader, @NotNull ReceiverDataModel data) throws IOException {
        String rinexVersion = data.getRinexVersionType().getVersion();
        if (rinexVersion != null) {
            State rinexObservationReader = ReadRinexObservationsFactory.rinexObservationReaders.get(rinexVersion);
            if (rinexObservationReader != null) {
                rinexObservationReader.read(reader, data);
            }
        }
    }
}
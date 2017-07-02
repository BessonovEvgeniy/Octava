package service.observations.rinex;

import model.observations.TNP;

import java.io.IOException;
import java.io.InputStream;

public class ReadObservations implements State {

    @Override
    public boolean read(InputStream uploadedInputStream, TNP tnp) throws IOException, InvalidRinexDataException {
        return true;
    }
}

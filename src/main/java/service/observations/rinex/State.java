package service.observations.rinex;

import model.observations.TNP;

import java.io.IOException;
import java.io.InputStream;

public interface State {
    boolean read(InputStream uploadedInputStream, TNP tnp) throws IOException, InvalidRinexDataException;
}

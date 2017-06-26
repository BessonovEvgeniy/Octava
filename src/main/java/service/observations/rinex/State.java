package service.observations.rinex;

import model.observations.TNP;

import java.io.BufferedReader;
import java.io.IOException;

public interface State {
    void read(BufferedReader reader, TNP tnp) throws IOException;
}
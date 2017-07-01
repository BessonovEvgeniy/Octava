package service.observations.rinex;

import model.observations.TNP;

import java.io.IOException;

public interface RinexService {

    TNP readRinex(String fileName) throws IOException;
}

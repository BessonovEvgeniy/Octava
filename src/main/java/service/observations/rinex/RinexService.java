package service.observations.rinex;

import model.observations.TNP;

public interface RinexService {

    TNP readRinex(String fileName);
}

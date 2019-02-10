package service.impl.rinex;

import model.observation.ReceiverDataModel;
import service.RinexSectionReader;

import java.io.BufferedReader;

public abstract class AbstractReadRinexObservations implements RinexSectionReader {

    public abstract void read(BufferedReader reader, ReceiverDataModel data);
}
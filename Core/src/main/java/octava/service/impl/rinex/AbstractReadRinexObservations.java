package octava.service.impl.rinex;

import octava.model.observation.ReceiverDataModel;
import octava.service.RinexSectionReader;

import java.io.BufferedReader;

public abstract class AbstractReadRinexObservations implements RinexSectionReader {

    public abstract void read(BufferedReader reader, ReceiverDataModel data);
}
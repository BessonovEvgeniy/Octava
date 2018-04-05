package ppa.service.impl.rinex;

import ppa.model.observation.ReceiverDataModel;
import ppa.service.RinexReader;

import java.io.BufferedReader;

public abstract class AbstractReadRinexObservations implements RinexReader {
    @Override
    public abstract void read(BufferedReader reader, ReceiverDataModel data) throws Exception;
}
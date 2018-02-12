package rinex.service.impl.rinex.impl;

import rinex.model.observation.ReceiverDataModel;
import rinex.service.State;

import java.io.BufferedReader;

abstract class AbstractReadRinexObservations implements State {

    @Override
    public abstract void read(BufferedReader reader, ReceiverDataModel data) throws Exception;
}
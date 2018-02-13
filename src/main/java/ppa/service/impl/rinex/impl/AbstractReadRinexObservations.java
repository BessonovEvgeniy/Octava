package ppa.service.impl.rinex.impl;

import ppa.model.observation.ReceiverDataModel;
import ppa.service.State;

import java.io.BufferedReader;

abstract class AbstractReadRinexObservations implements State {

    @Override
    public abstract void read(BufferedReader reader, ReceiverDataModel data) throws Exception;
}
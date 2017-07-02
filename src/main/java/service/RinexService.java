package service;

import model.observations.ReceiverDataModel;

import java.io.BufferedReader;

public interface RinexService {

    ReceiverDataModel readRinex(BufferedReader reader) throws Exception;
}

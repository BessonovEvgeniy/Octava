package service;

import model.observations.ReceiverDataModel;

import java.io.IOException;

public interface RinexService {

    ReceiverDataModel readRinex(String fileName) throws IOException;
}

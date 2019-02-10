package service;


import model.observation.ReceiverDataModel;

import java.io.BufferedReader;

public interface RinexSectionReader {

    void read(BufferedReader reader, ReceiverDataModel data);
}
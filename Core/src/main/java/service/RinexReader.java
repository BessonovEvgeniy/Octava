package service;


import model.observation.ReceiverDataModel;

import java.io.File;
import java.io.InputStream;

public interface RinexReader {

    ReceiverDataModel read(InputStream inputStream);

    ReceiverDataModel read(File file);
}
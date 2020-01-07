package octava.service;


import octava.model.observation.ReceiverDataModel;

import java.io.File;
import java.io.InputStream;

public interface RinexReader {

    ReceiverDataModel read(InputStream inputStream);

    ReceiverDataModel read(File file);
}
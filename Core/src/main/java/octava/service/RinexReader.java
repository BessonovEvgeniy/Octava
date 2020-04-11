package octava.service;


import octava.model.observation.ReceiverDataModel;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

public interface RinexReader {

    ReceiverDataModel read(InputStream inputStream);

    ReceiverDataModel read(File file);
}
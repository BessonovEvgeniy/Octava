package octava.service;


import octava.model.observation.ReceiverDataModel;
import octava.model.rinex.RinexFileMediaModel;

import java.io.File;
import java.io.InputStream;

public interface RinexReaderService {

    ReceiverDataModel read(InputStream inputStream);

    ReceiverDataModel read(File file);

    ReceiverDataModel read(RinexFileMediaModel file);
}
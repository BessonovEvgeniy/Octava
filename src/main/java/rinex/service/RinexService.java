package rinex.service;

import rinex.model.observation.ReceiverDataModel;

import java.io.InputStream;

public interface RinexService  {

    ReceiverDataModel readRinex(InputStream inputStream) throws Exception;
}

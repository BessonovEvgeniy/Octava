package rinex.service;

import rinex.model.observations.ReceiverDataModel;

import java.io.InputStream;

public interface RinexService  {

    ReceiverDataModel readRinex(InputStream inputStream) throws Exception;
}

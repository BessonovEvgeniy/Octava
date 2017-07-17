package rinex.service;

import rinex.model.observations.ReceiverDataModel;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.InputStream;

@Configurable
public interface RinexService  {

    ReceiverDataModel readRinex(InputStream inputStream) throws Exception;
}

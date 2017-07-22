package rinex.service;

import org.springframework.stereotype.Service;
import rinex.model.observations.ReceiverDataModel;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.InputStream;

@Service
public interface RinexService  {

    ReceiverDataModel readRinex(InputStream inputStream) throws Exception;
}

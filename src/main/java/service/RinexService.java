package service;

import model.observations.ReceiverDataModel;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface RinexService {

    ReceiverDataModel readRinex(InputStream inputStream) throws Exception;
}

package rinex.service;

import org.springframework.web.multipart.MultipartFile;
import rinex.model.observation.ReceiverDataModel;

import java.io.InputStream;

public interface RinexService  {

    void validateRinex(MultipartFile rinexFile) throws Exception;

    ReceiverDataModel readRinex(InputStream inputStream) throws Exception;
}

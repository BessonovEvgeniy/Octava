package rinex.service;

import org.springframework.web.multipart.MultipartFile;
import rinex.model.observation.ReceiverDataModel;
import rinex.model.process.Process;

import java.util.List;

public interface RinexService  {

    void validateRinex(MultipartFile rinexFile) throws Exception;

    List<ReceiverDataModel> readRinex(Process process) throws Exception;
}

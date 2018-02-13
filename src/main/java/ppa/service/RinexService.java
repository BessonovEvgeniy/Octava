package ppa.service;

import org.springframework.web.multipart.MultipartFile;
import ppa.model.observation.ReceiverDataModel;
import business.model.process.Process;

import java.util.List;

public interface RinexService  {

    void validateRinex(MultipartFile rinexFile) throws Exception;

    List<ReceiverDataModel> readRinex(Process process) throws Exception;
}

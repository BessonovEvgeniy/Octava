package ppa.service;

import org.springframework.web.multipart.MultipartFile;
import ppa.model.observation.ReceiverDataModel;

import java.io.File;

public interface RinexService {

    void validateRinex(MultipartFile rinexFile) throws Exception;

    ReceiverDataModel readRinex(File file) throws Exception;
}

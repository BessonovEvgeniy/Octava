package ppa.service.impl.storage;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import business.model.project.Project;
import ppa.service.StorageService;

import java.io.File;

@Service("localStorage")
@PropertySource("classpath:local.storage.properties")
public class LocalStorage implements StorageService {

    @Value("${localStorageFolder}")
    String path;

    @Override
    public String store(MultipartFile multipartFile, Project project) throws Exception {
        File targetFile = new File(path + multipartFile.getOriginalFilename());
        FileUtils.writeByteArrayToFile(targetFile, multipartFile.getBytes());
        return targetFile.getAbsolutePath();
    }

    @Override
    public String store(File file, Project project) throws Exception {
        return "";
    }

    @Override
    public void delete(String fileName) {

    }

    @Override
    public void createFolder(String folderName) {

    }

    @Override
    public void deleteFolder(String folderName) {

    }
}

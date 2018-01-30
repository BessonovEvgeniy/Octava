package rinex.service.impl.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rinex.model.project.Project;
import rinex.service.StorageService;

import java.io.File;

@Service("localStorage")
public class LocalStorage implements StorageService {

    @Override
    public String store(MultipartFile file, Project project) throws Exception {
        return "";
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

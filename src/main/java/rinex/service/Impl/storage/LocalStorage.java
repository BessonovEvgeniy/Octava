package rinex.service.Impl.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rinex.service.StorageService;

@Service
public class LocalStorage implements StorageService {

    @Override
    public String store(MultipartFile file, String fileName) throws Exception {
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

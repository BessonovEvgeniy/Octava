package rinex.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String store(MultipartFile file, String fileName) throws Exception;

    void delete(String fileName);

    void createFolder(String folderName);

    void deleteFolder(String folderName);
}

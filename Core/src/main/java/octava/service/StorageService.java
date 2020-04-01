package octava.service;

import octava.model.media.MediaModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface StorageService<T extends MediaModel> {

    List<T> store(List<MultipartFile> file);

    T store(MultipartFile file);

    File store(File file);

    void delete(String fileName);

    void createFolder(String folderName);

    void deleteFolder(String folderName);
}

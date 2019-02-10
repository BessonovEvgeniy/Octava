package service;

import model.StoredFileModel;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface StorageService {

    List<StoredFileModel> store(List<MultipartFile> file) throws FileUploadException;

    StoredFileModel store(MultipartFile file) throws FileUploadException;

    File store(File file) throws FileUploadException;

    void delete(String fileName);

    void createFolder(String folderName);

    void deleteFolder(String folderName);
}

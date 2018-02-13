package ppa.service;

import org.springframework.web.multipart.MultipartFile;
import business.model.project.Project;

import java.io.File;

public interface StorageService {

    String store(MultipartFile file, Project project) throws Exception;

    String store(File file, Project project) throws Exception;

    void delete(String fileName);

    void createFolder(String folderName);

    void deleteFolder(String folderName);
}

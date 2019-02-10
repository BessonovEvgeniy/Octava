package service.impl.storage;

import model.StoredFileModel;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import service.StorageService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Service("localStorage")
@PropertySource("classpath:local.storage.properties")
public class LocalStorage implements StorageService {

    @Value("${localStorageFolder}")
    private String path;

    @Override
    public List<StoredFileModel> store(List<MultipartFile> multipartFiles) throws FileUploadException {

        return null;
    }

    @Override
    public StoredFileModel store(MultipartFile file) throws FileUploadException {
        return null;
    }

    @Override
    public File store(File file) throws FileUploadException {
        return null;
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

    private Function<MultipartFile, String> storage = multipartFile -> {

        String fileName = multipartFile.getOriginalFilename();

        File targetFile = new File(path + fileName);

        try {
            multipartFile.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return Strings.EMPTY;
        }

        return targetFile.getAbsolutePath();
    };

}

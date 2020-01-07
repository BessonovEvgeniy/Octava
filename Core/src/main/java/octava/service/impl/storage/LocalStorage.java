package octava.service.impl.storage;

import octava.model.NullStoredFileModel;
import octava.model.StoredFileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import octava.service.StorageService;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.negate;

@Service("localStorage")
@PropertySource("classpath:core.storage.properties")
public class LocalStorage implements StorageService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalStorage.class);

    @Value("${storage.local.folder}")
    private String path;

    @Override
    public List<StoredFileModel> store(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream().map(this::store).collect(Collectors.toList());
    }

    @Override
    public StoredFileModel store(MultipartFile file) {
        return storage.apply(file);
    }

    @Override
    public File store(File file) {
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

    private Function<MultipartFile, StoredFileModel> storage = multipartFile -> {

        final String userPath = "admin";
        final File folder = new File(path + "/" + userPath);

        if (negate(folder.exists())) {
            folder.mkdir();
        }

        StoredFileModel storedFileModel = new NullStoredFileModel();

        final String fileName = multipartFile.getOriginalFilename();

        try {
            final File targetFile = new File(folder + fileName);

            multipartFile.transferTo(targetFile);
            storedFileModel = new StoredFileModel();

        } catch (IOException e) {
            LOG.error(MessageFormat.format("Can't save file {0}", fileName));
        }
        storedFileModel.setPath(folder.getPath());
        storedFileModel.setFileName(fileName);

        return storedFileModel;
    };

}

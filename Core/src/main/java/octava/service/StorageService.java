package octava.service;

import octava.model.media.MediaModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public interface StorageService<T extends MediaModel> {

    Logger LOG = LoggerFactory.getLogger(StorageService.class);

    List<T> store(List<MultipartFile> file);

    T store(MultipartFile file);

    File store(File file);

    void delete(String fileName);

    void createFolder(String folderName);

    void deleteFolder(String folderName);

    @Async
    default void store(final MultipartFile file, final String fileName) {
        try {
            final File targetFile = new File(fileName);
            file.transferTo(targetFile);
        } catch (IOException e) {
            LOG.error(MessageFormat.format("Can't save file {0}", fileName));
        }
    }
}

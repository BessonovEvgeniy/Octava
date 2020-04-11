package octava.service.impl.storage;

import octava.dao.MediaRepository;
import octava.model.media.MediaModel;
import octava.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.negate;

public class LocalStorage<T extends MediaModel> implements StorageService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(LocalStorage.class);

    @Resource
    private MediaRepository<T> mediaRepository;

    @Autowired
    private Environment environment;

    @Override
    public List<T> store(final List<MultipartFile> multipartFiles) {
        return multipartFiles.stream().map(this::store).collect(Collectors.toList());
    }

    @Override
    public T store(final MultipartFile file) {

        final StringJoiner fullPath = new StringJoiner("\\");

        final String userPath = "admin";

        fullPath.add(environment.getProperty("storage.local.folder")).add(userPath);

        final String path = fullPath.toString();
        final File folder = new File(path);

        if (negate(folder.exists())) {
            folder.mkdir();
        }

        T media = mediaRepository.create();
        final String fileName = fullPath.add(file.getOriginalFilename()).toString();

        try {
            final File targetFile = new File(fileName);

            file.transferTo(targetFile);
            media.setPath(folder.getPath());
            media.setFileName(fileName);

        } catch (IOException e) {
            LOG.error(MessageFormat.format("Can't save file {0}", fileName));
        }

        return media;
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
}

package octava.service.impl.storage;

import octava.model.media.MediaModel;
import octava.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.BooleanUtils.negate;

@PropertySource("classpath:core.storage.properties")
public class LocalStorage<T extends MediaModel> implements StorageService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(LocalStorage.class);

    private Class<T> entityClass;

    @Value("${storage.local.folder}")
    private String path;

    public LocalStorage() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public List<T> store(final List<MultipartFile> multipartFiles) {
        return multipartFiles.stream().map(this::store).collect(Collectors.toList());
    }

    @Override
    public T store(final MultipartFile file) {
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

    private Function<MultipartFile, T> storage = multipartFile -> {

        final String userPath = "admin";
        final File folder = new File(path + "/" + userPath + "/");

        if (negate(folder.exists())) {
            folder.mkdir();
        }

        T media = null;

        final String fileName = multipartFile.getOriginalFilename();

        try {
            final File targetFile = new File(folder + fileName);

            multipartFile.transferTo(targetFile);
            media = createMedia();
            media.setPath(folder.getPath());
            media.setFileName(fileName);

        } catch (IOException e) {
            LOG.error(MessageFormat.format("Can't save file {0}", fileName));
        }

        return media;
    };

    protected T createMedia() {
        try {
            return entityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.error("Can not create media", e);
            return null;
        }
    }

}

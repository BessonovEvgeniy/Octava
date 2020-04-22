package octava.service.impl.storage;

import octava.converter.AbstractPopulatingConverter;
import octava.dao.MediaRepository;
import octava.model.media.MediaModel;
import octava.service.StorageService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class LocalStorage<T extends MediaModel> implements StorageService<T> {

    @Resource
    private MediaRepository<T> mediaRepository;

    @Resource
    private AbstractPopulatingConverter<MultipartFile, T> multipartFile2MediaConverter;

    @Override
    public List<T> store(final List<MultipartFile> multipartFiles) {
        return multipartFiles.stream().map(this::store).collect(Collectors.toList());
    }

    @Override
    public T store(final MultipartFile file) {

        T media = mediaRepository.create();

        multipartFile2MediaConverter.convert(file, media);

        mediaRepository.save(media);

        store(file, media);

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

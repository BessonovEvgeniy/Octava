package octava.service.impl;

import octava.dao.impl.MediaRepositoryImpl;
import octava.model.media.MediaModel;
import octava.service.MediaService;
import org.springframework.stereotype.Service;

@Service("mediaService")
public class MediaServiceImpl<T extends MediaModel, P extends MediaRepositoryImpl<T>> extends BaseServiceImpl <T, P> implements MediaService<T> {
}

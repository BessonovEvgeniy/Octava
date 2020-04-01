package octava.dao.impl;

import octava.dao.MediaRepository;
import octava.model.media.MediaModel;
import org.springframework.stereotype.Repository;

@Repository("mediaRepository")
public abstract class MediaRepositoryImpl<T extends MediaModel> extends BaseRepositoryImpl<T> implements MediaRepository<T> {
}

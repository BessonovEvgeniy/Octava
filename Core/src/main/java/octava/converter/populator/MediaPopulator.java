package octava.converter.populator;

import octava.converter.Populator;
import octava.dto.MediaDto;
import octava.model.media.MediaModel;

public class MediaPopulator<SOURCE extends MediaModel, TARGET extends MediaDto> implements Populator<SOURCE, TARGET> {

    @Override
    public void populate(MediaModel source, MediaDto target) {

        target.setFileName(source.getFileName());
        target.setPath(source.getPath());
        target.setUrl(source.getUrl());
    }
}

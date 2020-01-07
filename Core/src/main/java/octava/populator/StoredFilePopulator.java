package octava.populator;

import octava.converter.Populator;
import octava.dto.StoredFileDto;
import octava.model.StoredFileModel;
import org.springframework.stereotype.Component;

@Component
public class StoredFilePopulator implements Populator<StoredFileModel, StoredFileDto> {

    @Override
    public void populate(StoredFileModel source, StoredFileDto target) {

        target.setFileName(source.getFileName());
        target.setPath(source.getPath());
        target.setUrl(source.getUrl());
    }
}

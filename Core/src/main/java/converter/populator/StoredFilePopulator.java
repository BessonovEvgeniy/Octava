package converter.populator;

import converter.Populator;
import dto.RinexFileDto;
import dto.StoredFileDto;
import model.StoredFileModel;
import model.rinex.RinexFileModel;

public class StoredFilePopulator<SOURCE extends RinexFileModel, TARGET extends RinexFileDto> implements Populator<SOURCE, TARGET> {

    @Override
    public void populate(SOURCE source, TARGET target) {
        StoredFileModel storedFile = source.getStoredFile();
        StoredFileDto storedFileDto = new StoredFileDto();

        storedFileDto.setFileName(storedFile.getFileName());
        storedFileDto.setPath(storedFile.getPath());
        storedFileDto.setUrl(storedFile.getUrl());
    }
}

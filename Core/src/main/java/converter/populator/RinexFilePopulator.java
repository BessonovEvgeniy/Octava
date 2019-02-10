package converter.populator;

import converter.Populator;
import dto.RinexFileDto;
import model.rinex.RinexFileModel;

public class RinexFilePopulator<SOURCE extends RinexFileModel, TARGET extends RinexFileDto> implements Populator<SOURCE, TARGET> {

    @Override
    public void populate(SOURCE source, TARGET target) {
        target.setName(source.getName());
        target.setStatus(source.getStatus().name());
    }
}

package octava.converter.populator;

import octava.dto.RinexFileDto;
import octava.model.rinex.RinexFileMediaModel;

public class RinexFilePopulator<SOURCE extends RinexFileMediaModel, TARGET extends RinexFileDto> extends MediaPopulator<SOURCE, TARGET> {

    @Override
    public void populate(SOURCE source, TARGET target) {
        super.populate(source, target);
        target.setStatus(source.getStatus().name());
    }
}

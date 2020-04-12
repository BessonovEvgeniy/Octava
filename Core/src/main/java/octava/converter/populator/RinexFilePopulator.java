package octava.converter.populator;

import octava.dto.RinexFileMediaDto;
import octava.model.rinex.RinexFileMediaModel;

public class RinexFilePopulator<SOURCE extends RinexFileMediaModel, TARGET extends RinexFileMediaDto> extends MediaPopulator<SOURCE, TARGET> {

    @Override
    public void populate(SOURCE source, TARGET target) {
        super.populate(source, target);
        target.setStatus(source.getStatus().name());
    }
}

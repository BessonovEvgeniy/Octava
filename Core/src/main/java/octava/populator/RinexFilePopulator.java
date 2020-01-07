package octava.populator;

import octava.converter.Populator;
import octava.dto.RinexFileDto;
import octava.model.rinex.RinexFileModel;

import static org.apache.commons.lang3.StringUtils.defaultString;

public class RinexFilePopulator<SOURCE extends RinexFileModel, TARGET extends RinexFileDto> implements Populator<SOURCE, TARGET> {

    @Override
    public void populate(SOURCE source, TARGET target) {

        target.setName(defaultString(source.getName()));
        target.setStatus(source.getStatus().name());
    }
}

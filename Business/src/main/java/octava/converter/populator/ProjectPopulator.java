package octava.converter.populator;

import octava.converter.AbstractPopulatingConverter;
import octava.converter.Populator;
import octava.dto.ProjectDto;
import octava.dto.RinexFileMediaDto;
import octava.dto.UserDto;
import octava.model.project.ProjectModel;
import octava.model.rinex.RinexFileMediaModel;
import octava.model.user.UserPrincipal;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Resource;

public class ProjectPopulator implements Populator<ProjectModel, ProjectDto> {

    @Resource
    private Converter<UserPrincipal, UserDto> userConverter;

    @Resource
    private AbstractPopulatingConverter<RinexFileMediaModel, RinexFileMediaDto> rinexFileConverter;

    @Override
    public void populate(final ProjectModel source, final ProjectDto target) {

        target.setName(source.getName());
        target.setCreatedBy(userConverter.convert(source.getCreatedBy()));
        target.setRinexFiles(rinexFileConverter.convertAll(source.getRinexFileMediaModels()));
        target.setStatus(source.getStatus().name());
    }
}

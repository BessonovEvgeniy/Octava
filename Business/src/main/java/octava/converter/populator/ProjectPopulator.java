package octava.converter.populator;

import octava.converter.Populator;
import octava.dto.ProjectDto;
import octava.dto.UserDto;
import octava.model.project.ProjectModel;
import octava.model.user.UserPrincipal;
import org.springframework.core.convert.converter.Converter;

import javax.annotation.Resource;

public class ProjectPopulator implements Populator<ProjectModel, ProjectDto> {

    @Resource
    private Converter<UserPrincipal, UserDto> userConverter;

    @Override
    public void populate(final ProjectModel source, final ProjectDto target) {

        target.setName(source.getName());
        target.setCreatedBy(userConverter.convert(source.getCreatedBy()));
        target.setStatus(source.getStatus().name());
    }
}

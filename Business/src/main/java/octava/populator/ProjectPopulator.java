package octava.populator;

import octava.converter.Populator;
import octava.dto.ProjectDto;
import octava.model.project.ProjectModel;

public class ProjectPopulator implements Populator<ProjectModel, ProjectDto> {

    @Override
    public void populate(final ProjectModel source, final ProjectDto target) {

        target.setName(source.getName());
        target.setStatus(source.getStatus().name());
        target.setCreated(source.getCreated());
    }
}

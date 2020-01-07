package octava.populator;

import octava.converter.Populator;
import octava.dto.ProjectDto;
import octava.model.project.ProjectModel;

public class ProjectPopulator implements Populator<ProjectModel, ProjectDto> {

    @Override
    public void populate(ProjectModel source, ProjectDto target) {

        target.setProjectName(source.getName());
        target.setStatus(source.getStatus().name());
    }
}

package converter.populator;

import converter.Populator;
import dto.ProjectDto;
import model.project.ProjectModel;

public class ProjectPopulator implements Populator<ProjectModel, ProjectDto> {

    @Override
    public void populate(ProjectModel source, ProjectDto target) {

        target.setProjectName(source.getName());
        target.setStatus(source.getStatus().name());
    }
}

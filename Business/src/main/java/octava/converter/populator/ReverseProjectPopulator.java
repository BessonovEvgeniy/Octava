package octava.converter.populator;

import octava.converter.Populator;
import octava.dto.ProjectDto;
import octava.model.project.ProjectModel;

public class ReverseProjectPopulator implements Populator<ProjectDto, ProjectModel> {

    @Override
    public void populate(final ProjectDto target, final ProjectModel source) {

        source.setName(target.getName());
        source.setStatus(ProjectModel.Status.valueOf(target.getStatus()));
    }
}

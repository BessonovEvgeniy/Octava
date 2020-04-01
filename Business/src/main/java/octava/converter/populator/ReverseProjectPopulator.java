package octava.converter.populator;

import octava.converter.Populator;
import octava.dto.ProjectDto;
import octava.model.project.ProjectModel;

public class ReverseProjectPopulator implements Populator<ProjectDto, ProjectModel> {

    @Override
    public void populate(final ProjectDto source, final ProjectModel target) {

        target.setName(source.getName());
    }
}

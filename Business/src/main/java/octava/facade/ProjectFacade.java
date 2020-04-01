package octava.facade;

import octava.dto.ProjectDto;

import java.util.List;

public interface ProjectFacade {

    ProjectDto create(ProjectDto project);

    ProjectDto update(ProjectDto project);

    ProjectDto get(String projectName);

    List<ProjectDto> getAll();

    ProjectDto addRinexFiles(ProjectDto projectDto);
}

package octava.facade;

import octava.dto.ProjectDto;

import java.security.Principal;
import java.util.List;

public interface ProjectFacade {

    ProjectDto create(ProjectDto project, Principal principal);

    List<ProjectDto> projectsByUser(Principal principal);
}

package octava.facade;


import octava.dto.ProjectDto;
import octava.model.project.ProjectModel;
import octava.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import octava.service.ProjectService;
import octava.service.UserService;

import javax.annotation.Resource;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Component("projectFacade")
public class ProjectFacade {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectFacade.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Resource(name = "projectConverter")
    private Converter<ProjectModel, ProjectDto> converter;

    public ProjectDto create(final String projectName, final String login) {

        final User user = userService.getByLogin(login);

        final ProjectModel projectModel = new ProjectModel(projectName, user);

        try {
            projectService.insert(projectModel);
        } catch (SQLException e) {
            LOG.error("Can't create projectModel " + projectName + "for User " + login);
            e.printStackTrace();
            throw new RuntimeException();
        }
        ProjectDto projectDto = convert(projectModel);

        return projectDto;
    }

    public ProjectDto convert(ProjectModel projectModel) {
        return converter.convert(projectModel);
    }

    public List<ProjectDto> projectsByUser(Principal principal) {

        List<ProjectModel> projectModels = projectService.getAllByUser(principal);
        List<ProjectDto> projectDtoList = projectModels.stream().map(this::convert).collect(Collectors.toList());

        return projectDtoList;
    }
}

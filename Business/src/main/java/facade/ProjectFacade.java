package facade;


import dto.ProjectDto;
import model.project.ProjectModel;
import model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import service.ProjectService;
import service.UserService;

import javax.annotation.Resource;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Component("projectFacade")
public class ProjectFacade {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectFacade.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Resource(name = "projectConverter")
    private Converter<ProjectModel, ProjectDto> converter;

    public ProjectModel create(String projectName, String login) {

        User user = userService.getByLogin(login);

        ProjectModel projectModel = new ProjectModel(projectName, user);

        try {
            projectService.insert(projectModel);
        } catch (SQLException e) {
            LOG.error("Can't create projectModel " + projectName + "for User " + login);
            e.printStackTrace();
            throw new RuntimeException();
        }

        return projectModel;
    }

    public ProjectDto convert(ProjectModel projectModel) {
        return converter.convert(projectModel);
    }

    public List<ProjectModel> projectsByUser(Principal principal) {



        return null;
    }
}

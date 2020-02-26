package octava.facade.impl;


import octava.converter.AbstractPopulatingConverter;
import octava.dto.ProjectDto;
import octava.exception.ProjectException;
import octava.facade.ProjectFacade;
import octava.model.project.ProjectModel;
import octava.model.user.User;
import octava.service.ProjectService;
import octava.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.Principal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component("projectFacade")
public class ProjectFacadeImpl implements ProjectFacade {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectFacadeImpl.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Resource
    private AbstractPopulatingConverter<ProjectModel, ProjectDto> projectConverter;

    @Resource
    private AbstractPopulatingConverter<ProjectDto, ProjectModel> reverseProjectConverter;

    @Override
    public ProjectDto create(final ProjectDto project, final Principal principal) {

        final String login = principal.getName();
        final User user = userService.getByLogin(login);

        final String projectName = project.getName();
        final ProjectModel projectModel = new ProjectModel(projectName, user);

        try {
            projectService.insert(projectModel);
        } catch (final SQLException e) {
            LOG.error(MessageFormat.format("Can\\'t create projectModel {0}, for User {1}", projectModel, login), e);
            throw new ProjectException();
        }
        final ProjectDto projectDto = projectConverter.convert(projectModel);

        return projectDto;
    }

    @Override
    public ProjectDto update(final ProjectDto projectDto, final Principal principal) {

        final String userLogin = principal.getName();

        final String projectName = projectDto.getName();

        final ProjectModel projectModel = projectService.getProject(projectName, userLogin);

        reverseProjectConverter.convert(projectDto, projectModel);

        try {
            projectService.update(projectModel);
        } catch (final SQLException e) {
            LOG.error(MessageFormat.format("Can\\'t create projectModel {0}, for User {1}", projectModel, userLogin), e);
            throw new ProjectException();
        }
        final ProjectDto updatedProjectDto = projectConverter.convert(projectModel);

        return updatedProjectDto;
    }

    @Override
    public ProjectDto get(final String projectName, final Principal principal) {
        final String userLogin = principal.getName();

        final ProjectModel projectModel = projectService.getProject(projectName, userLogin);

        final ProjectDto projectDto = projectConverter.convert(projectModel);

        return projectDto;
    }

    @Override
    public List<ProjectDto> projectsByUser(final Principal principal) {

        final List<ProjectModel> projectModels = projectService.getAllByUser(principal);
        final List<ProjectDto> projectDtoList = projectConverter.convertAll(projectModels);

        return projectDtoList;
    }
}

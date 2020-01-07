package octava.service.impl.project;

import octava.dao.ProjectRepository;
import octava.model.project.ProjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import octava.service.ProjectService;
import octava.service.impl.BaseServiceImpl;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;


@Service("projectService")
public class ProjectServiceImpl<T extends ProjectModel, P extends ProjectRepository<T>> extends BaseServiceImpl<T, P> implements ProjectService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public List<T> getAllByUser(Principal principal) {
        return principal == null ? Collections.EMPTY_LIST : getAllByUser(principal.getName());
    }

    @Override
    public List<T> getAllByUser(String login) {

        List<T> projects = Collections.EMPTY_LIST;

        try {
            projects = dao.findAllByUser(login);
        } catch (SQLException e) {
            LOG.error("Can't find any project for user " + login);
        }

        return projects;
    }

    @Override
    public Optional<T> getProject(String projectName, String login) {

        ProjectModel projectModel = null;
        try {
            projectModel = dao.findProject(projectName, login);
        } catch (SQLException e) {

            StringJoiner msg = new StringJoiner(" ");
            msg.add("Can't find projectModel").add(projectName).add("for user").add(login);

            LOG.error(msg.toString());
        }

        return Optional.ofNullable((T) projectModel);
    }
}

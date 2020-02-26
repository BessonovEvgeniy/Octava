package octava.service.impl;

import octava.dao.ProjectRepository;
import octava.model.project.ProjectModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import octava.service.ProjectService;

import java.security.Principal;
import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;


@Service("projectService")
public class ProjectServiceImpl<T extends ProjectModel, P extends ProjectRepository<T>> extends BaseServiceImpl<T, P> implements ProjectService<T> {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public List<T> getAllByUser(Principal principal) {
        return emptyIfNull(getAllByUser(principal.getName()));
    }

    @Override
    public List<T> getAllByUser(String login) {
        return dao.findAllByUser(login);
    }

    @Override
    public T getProject(String projectName, String login) {
        return dao.findProject(projectName, login);
    }
}

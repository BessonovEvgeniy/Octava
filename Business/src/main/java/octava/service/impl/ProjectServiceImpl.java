package octava.service.impl;

import octava.dao.ProjectRepository;
import octava.model.project.ProjectModel;
import octava.service.ProjectService;
import org.springframework.stereotype.Service;


@Service("projectService")
public class ProjectServiceImpl<T extends ProjectModel, P extends ProjectRepository<T>> extends BaseServiceImpl<T, P> implements ProjectService<T> {

    @Override
    public T getProject(String projectName) {
        return dao.findProject(projectName);
    }
}

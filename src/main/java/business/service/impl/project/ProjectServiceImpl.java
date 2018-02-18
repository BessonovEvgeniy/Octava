package business.service.impl.project;

import business.dao.ProjectRepository;
import business.model.project.Project;
import business.service.ProjectService;
import business.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ProjectServiceImpl extends BaseServiceImpl<Project, ProjectRepository> implements ProjectService {

}

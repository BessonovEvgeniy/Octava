package rinex.service.impl.project;

import org.springframework.stereotype.Service;
import rinex.dao.ProjectRepository;
import rinex.model.project.Project;
import rinex.service.ProjectService;
import rinex.service.impl.BaseServiceImpl;


@Service
public class ProjectServiceImpl extends BaseServiceImpl<Project, ProjectRepository> implements ProjectService{ }

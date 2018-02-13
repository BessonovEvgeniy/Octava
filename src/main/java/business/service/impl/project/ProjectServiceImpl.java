package business.service.impl.project;

import org.springframework.stereotype.Service;
import business.dao.ProjectRepository;
import business.model.project.Project;
import business.service.ProjectService;
import ppa.service.impl.BaseServiceImpl;


@Service
public class ProjectServiceImpl extends BaseServiceImpl<Project, ProjectRepository> implements ProjectService{ }

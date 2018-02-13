package business.dao.jpa;

import org.springframework.stereotype.Repository;
import business.dao.ProjectRepository;
import business.model.project.Project;
import ppa.dao.jpa.BaseRepositoryImpl;

@Repository
public class ProjectRepositoryImpl extends BaseRepositoryImpl<Project> implements ProjectRepository { }

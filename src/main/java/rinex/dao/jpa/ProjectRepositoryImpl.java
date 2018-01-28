package rinex.dao.jpa;

import org.springframework.stereotype.Repository;
import rinex.dao.ProjectRepository;
import rinex.model.project.Project;

@Repository
public class ProjectRepositoryImpl extends BaseRepositoryImpl<Project> implements ProjectRepository { }

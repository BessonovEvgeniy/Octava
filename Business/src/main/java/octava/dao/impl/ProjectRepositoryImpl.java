package octava.dao.impl;

import octava.dao.ProjectRepository;
import octava.model.project.ProjectModel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ProjectRepositoryImpl extends BaseRepositoryImpl<ProjectModel> implements ProjectRepository<ProjectModel> {

    @Override
    public List<ProjectModel> findAll() {
        final String login = "admin";

        final Query query = em.createQuery("SELECT p FROM ProjectModel p INNER JOIN p.createdBy u WHERE u.login = :login", ProjectModel.class);

        query.setParameter("login", login);

        return query.getResultList();
    }

    @Override
    public ProjectModel findProject(final String name) {
        final String login = "admin";

        final Query query = em.createQuery("SELECT p FROM ProjectModel p INNER JOIN p.createdBy u WHERE p.name = :name AND u.login = :login", ProjectModel.class);

        query.setParameter("name", name);
        query.setParameter("login", login);

        return (ProjectModel) query.getSingleResult();
    }
}

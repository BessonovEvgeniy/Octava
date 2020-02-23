package octava.dao.jpa;

import octava.dao.ProjectRepository;
import octava.model.project.ProjectModel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectRepositoryImpl extends BaseRepositoryImpl<ProjectModel> implements ProjectRepository<ProjectModel> {

    @Override
    public List<ProjectModel> findAllByUser(final String login) {

        final Query query = em.createQuery("SELECT p FROM ProjectModel p INNER JOIN p.createdBy u WHERE u.principal.login = :login", ProjectModel.class);

        query.setParameter("login", login);

        return query.getResultList();
    }

    @Override
    public ProjectModel findProject(final String name, final String login) {

        final Query query = em.createQuery("SELECT p FROM ProjectModel p INNER JOIN p.createdBy u WHERE p.name = :name AND u.principal.login = :login", ProjectModel.class);

        query.setParameter("name", name);
        query.setParameter("login", login);

        return (ProjectModel) query.getSingleResult();
    }
}

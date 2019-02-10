package dao.jpa;

import dao.ProjectRepository;
import model.project.ProjectModel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectRepositoryImpl extends BaseRepositoryImpl<ProjectModel> implements ProjectRepository<ProjectModel> {

    @Override
    public List<ProjectModel> findAllByUser(String login) throws SQLException {

        Query query = em.createNativeQuery("SELECT p FROM Projects p JOIN Users u ON p.user_id = u.id WHERE u.login = :login", ProjectModel.class);

        query.setParameter("login", login);

        return query.getResultList();
    }

    @Override
    public ProjectModel findProject(String projectName, String login) throws SQLException {

        Query query = em.createNativeQuery("SELECT * FROM Projects p JOIN Users u ON p.user_id = u.id WHERE p.name = :projectName AND u.login = :login", ProjectModel.class);

        query.setParameter("projectName", projectName);
        query.setParameter("login", login);

        return (ProjectModel) query.getSingleResult();
    }
}

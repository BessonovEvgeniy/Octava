package octava.dao;

import octava.model.project.ProjectModel;

import java.sql.SQLException;
import java.util.List;

public interface ProjectRepository<T extends ProjectModel> extends BaseRepository<T> {

    List<T> findAllByUser(String login) throws SQLException;

    ProjectModel findProject(String projectName, String userId) throws SQLException;
}

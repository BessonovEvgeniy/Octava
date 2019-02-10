package service;

import model.project.ProjectModel;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProjectService<T extends ProjectModel> extends BaseService<T> {

    List<T> getAllByUser(Principal principal) throws SQLException;

    List<T> getAllByUser(String login) throws SQLException;

    Optional<T> getProject(String project, String login) throws SQLException;
}

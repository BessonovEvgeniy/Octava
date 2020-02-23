package octava.service;

import octava.model.project.ProjectModel;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ProjectService<T extends ProjectModel> extends BaseService<T> {

    List<T> getAllByUser(Principal principal);

    List<T> getAllByUser(String login);

    T getProject(String project, String login);
}

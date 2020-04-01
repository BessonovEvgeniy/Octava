package octava.service;

import octava.model.project.ProjectModel;

public interface ProjectService<T extends ProjectModel> extends BaseService<T> {

    T getProject(String project);
}

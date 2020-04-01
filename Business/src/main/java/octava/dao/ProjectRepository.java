package octava.dao;

import octava.model.project.ProjectModel;

import java.util.List;

public interface ProjectRepository<T extends ProjectModel> extends BaseRepository<T> {

    List<T> findAll();

    T findProject(String projectName);
}

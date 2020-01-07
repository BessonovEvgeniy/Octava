package octava.dao;

import octava.model.BaseModel;

import java.util.Collection;

public interface BaseRepository <T extends BaseModel> {

    Collection<T> findAll();

    T findOne(Long id);

    void save(T t);

    void delete(T t);

    boolean exists(Long id);

    long count();
}

package octava.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BaseService<T> {

    List<T> getAll();

    T getById(Long id);

    void delete(T o);

    void insert(T o);

    void update(T o);

    boolean isExists(Long id);

    long count();
}

package service;

import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Transactional
public interface BaseService<T> {

    List<T> getAll() throws SQLException;

    T getById(Long id) throws SQLException;

    void delete(T o) throws SQLException;

    void insert(T o) throws SQLException;

    void update(T o) throws SQLException;

    boolean isExists(Long id) throws SQLException;

    long count() throws SQLException;
}

package business.service.impl;

import business.dao.BaseRepository;
import business.model.BaseModel;
import business.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseServiceImpl<T extends BaseModel, P extends BaseRepository<T>> implements BaseService<T> {

    protected P dao;

    @Autowired
    public void setPersistence(P dao) {
        this.dao = dao;
    }

    @Override
    public List<T> getAll() throws SQLException {
        return (List<T>) (dao.findAll());
    }

    @Override
    public T getById(Long id) throws SQLException {
        return dao.findOne(id);
    }

    @Override
    public void delete(T o) throws SQLException {
        T d = getById(o.getId());
        dao.delete(d);
    }

    @Override
    public void insert(T o) throws SQLException {
        dao.save(o);
    }

    @Override
    public void update(T o) throws SQLException {
        dao.save(o);
    }

    @Override
    public boolean isExists(Long id) throws SQLException {
        return dao.exists(id);
    }

    @Override
    public long count() {
        return dao.count();
    }
}

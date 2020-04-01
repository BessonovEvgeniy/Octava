package octava.service.impl;

import octava.dao.BaseRepository;
import octava.model.BaseModel;
import octava.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseServiceImpl<T extends BaseModel, P extends BaseRepository<T>> implements BaseService<T> {

    protected P dao;

    @Autowired
    public void setPersistence(P dao) {
        this.dao = dao;
    }

    @Override
    public List<T> getAll() {
        return (List<T>) (dao.findAll());
    }

    @Override
    public T getById(final Long id) {
        return dao.findOne(id);
    }

    @Override
    public void delete(final T o) {
        final T d = getById(o.getId());
        dao.delete(d);
    }

    @Override
    public void insert(final T o) {
        dao.save(o);
    }

    @Override
    public void update(final T o) {
        dao.save(o);
    }

    @Override
    public boolean isExists(final Long id) {
        return dao.exists(id);
    }

    @Override
    public long count() {
        return dao.count();
    }
}

package business.dao.jpa;

import business.model.BaseModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ppa.dao.BaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

@Transactional
@Repository
public abstract class BaseRepositoryImpl<T extends BaseModel> implements BaseRepository<T> {

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager em;

    public BaseRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<T> findAll() {
        Query query = em.createQuery("SELECT t FROM " + entityClass.getName() + " t");
        return (List<T>) query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public T findOne(Long id) {
        return em.find(entityClass, id);
    }

    @Override
    public void save(T t) {
        if (t.getId() == null) {
            em.persist(t);
        } else {
            em.merge(t);
        }
    }

    @Override
    public void delete(T t) {
        em.remove(t);
    }

    @Override
    public boolean exists(Long id) {
        return findOne(id) != null;
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        Query query = em.createQuery("SELECT count(t) FROM " + entityClass.getName() + " t");
        return (long) query.getSingleResult();
    }
}

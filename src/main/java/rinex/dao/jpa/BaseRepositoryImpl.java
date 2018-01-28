package rinex.dao.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rinex.dao.BaseRepository;
import rinex.model.BaseModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

@Transactional
@Repository
public abstract class BaseRepositoryImpl <T extends BaseModel> implements BaseRepository<T> {

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager em;

    public BaseRepositoryImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public Collection<T> findAll() {
        Query query = em.createQuery("SELECT t FROM " + entityClass.getName() + " t");
        return (List<T>) query.getResultList();
    }

    public T findOne(Long id) {
        return em.find(entityClass, id);
    }

    public void save(T t) {
        if (t.getId() == null) {
            em.persist(t);
        } else {
            em.merge(t);
        }
    }

    public void delete(T t) {
        em.remove(t);
    }

    public boolean exists(Long id) {
        return findOne(id) != null;
    }

    public long count() {
        Query query = em.createQuery("SELECT count(t) FROM " + entityClass.getName() + " t");
        return (long) query.getSingleResult();
    }
}

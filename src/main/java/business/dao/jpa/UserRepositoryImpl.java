package business.dao.jpa;

import business.dao.UserRepository;
import business.model.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
public class UserRepositoryImpl<T extends User> extends BaseRepositoryImpl<User> implements UserRepository<User>{

    public T findUserByLogin(String login) {

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class).setParameter("login", login);

        return (T) query.getSingleResult();
    }
}

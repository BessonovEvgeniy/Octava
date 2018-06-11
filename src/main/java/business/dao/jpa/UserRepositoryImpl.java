package business.dao.jpa;

import business.dao.UserRepository;
import business.model.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class UserRepositoryImpl<T extends User> extends BaseRepositoryImpl<User> implements UserRepository<User>{

    public T findUserByLogin(String login) {

        Query query = em.createNativeQuery("SELECT user FROM User user WHERE user.login=" + login);


//        Query query = em.createQuery("SELECT user FROM User user WHERE user.login =:login");

//        query.setParameter("login", login);

        return (T) query.getSingleResult();
    }
}

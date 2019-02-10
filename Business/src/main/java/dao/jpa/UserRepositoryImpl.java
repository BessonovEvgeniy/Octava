package dao.jpa;

import dao.UserRepository;
import model.user.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository<User>{

    public Optional<User> findByLogin(String login) {

        if (StringUtils.isEmpty(login)) {
            return Optional.empty();
        }

        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.principal.login = :login", User.class).setParameter("login", login);

        return query.getResultList().stream().findFirst();
    }
}

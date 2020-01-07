package octava.dao.jpa;

import octava.dao.UserRepository;
import octava.model.user.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository<User>{

    public Optional<User> findByLogin(final String login) {

        if (StringUtils.isEmpty(login)) {
            return Optional.empty();
        }

        final Query query = em.createQuery("SELECT u FROM User u WHERE u.principal.login = :login", User.class).setParameter("login", login);

        return query.getResultList().stream().findFirst();
    }
}

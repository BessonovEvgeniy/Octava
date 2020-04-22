package octava.dao.impl;

import octava.dao.UserRepository;
import octava.model.user.UserPrincipal;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Optional;

@Repository("userRepository")
public class UserRepositoryImpl extends BaseRepositoryImpl<UserPrincipal> implements UserRepository<UserPrincipal>{

    public Optional<UserPrincipal> findByLogin(final String login) {

        if (StringUtils.isEmpty(login)) {
            return Optional.empty();
        }

        final Query query = em.createQuery("SELECT u FROM UserPrincipal u WHERE u.login = :login", UserPrincipal.class).setParameter("login", login);

        return query.getResultList().stream().findFirst();
    }
}

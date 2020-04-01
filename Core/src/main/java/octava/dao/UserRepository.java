package octava.dao;

import octava.model.user.UserPrincipal;

import java.util.Optional;

public interface UserRepository<T extends UserPrincipal> extends BaseRepository<T> {

    Optional<T> findByLogin(String login);
}

package dao;

import model.user.User;

import java.util.Optional;

public interface UserRepository<T extends User> extends BaseRepository<T> {

    Optional<T> findByLogin(String login);
}

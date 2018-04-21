package business.dao;

import business.model.user.User;

public interface UserRepository<T extends User> extends BaseRepository<User> {

    T findUserByLogin(String login);
}

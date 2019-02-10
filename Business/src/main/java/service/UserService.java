package service;

import model.user.User;

public interface UserService extends BaseService<User> {

    User getByLogin(String login);
}

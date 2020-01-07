package octava.service;

import octava.model.user.User;

public interface UserService extends BaseService<User> {

    User getByLogin(String login);
}

package octava.service;

import octava.model.user.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService extends BaseService<UserPrincipal> {

    UserDetails getByLogin(String login);
}

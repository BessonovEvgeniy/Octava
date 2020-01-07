package octava.service.impl.project;

import octava.dao.UserRepository;
import octava.model.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import octava.service.UserService;
import octava.service.impl.BaseServiceImpl;

@Service("userDetailsService")
public class UserDetailsServiceImpl extends BaseServiceImpl<User, UserRepository<User>> implements UserDetailsService, UserService {

    public final static String ERROR_MSG = "Can't find user %s";

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return getByLogin(login).getPrincipal();
    }

    @Override
    public User getByLogin(String login) {
        return dao.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(String.format(ERROR_MSG, login)));
    }
}

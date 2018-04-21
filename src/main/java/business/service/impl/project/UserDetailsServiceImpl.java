package business.service.impl.project;

import business.dao.UserRepository;
import business.model.user.User;
import business.model.user.UserSecurityDetails;
import business.service.impl.BaseServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl extends BaseServiceImpl<User, UserRepository<User>> implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        User user = dao.findUserByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException(login);
        }

        return new UserSecurityDetails(user);
    }
}

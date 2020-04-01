package octava.service.impl;

import octava.dao.UserRepository;
import octava.model.user.UserPrincipal;
import octava.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service("userDetailsService")
public class UserDetailsServiceImpl extends BaseServiceImpl<UserPrincipal, UserRepository<UserPrincipal>> implements UserDetailsService, UserService {

    public final static String ERROR_MSG = "Can't find user {0}";

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return getByLogin(login);
    }

    @Override
    public UserDetails getByLogin(String login) {
        return dao.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format(ERROR_MSG, login)));
    }
}

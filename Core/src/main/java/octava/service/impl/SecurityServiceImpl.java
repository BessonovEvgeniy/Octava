package octava.service.impl;

import octava.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    private SecurityContext securityContext;

    public Authentication getAuthentication() {
        if (isNull(securityContext)) {
            securityContext = SecurityContextHolder.getContext();
        }
        return securityContext.getAuthentication();
    }

    public String getLogin() {
        return getAuthentication().getName();
    }
}

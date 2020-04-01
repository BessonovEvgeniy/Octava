package octava.service;

import org.springframework.security.core.Authentication;

public interface SecurityService {

    Authentication getAuthentication();

    String getLogin();
}

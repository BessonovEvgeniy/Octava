package controller;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;

public class AbstractController {

    protected Principal resolvePrincipal(Principal principal) {
        return principal == null ? SecurityContextHolder.getContext().getAuthentication() : principal;
    }

    protected String getURLBase(HttpServletRequest request) throws MalformedURLException {

        URL requestURL = new URL(request.getRequestURL().toString());
        String port = requestURL.getPort() == -1 ? "" : ":" + requestURL.getPort();
        return requestURL.getProtocol() + "://" + requestURL.getHost() + port;
    }
}

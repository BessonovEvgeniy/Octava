package filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import handler.TokenSimpleUrlAuthenticationSuccessHandler;
import manager.NoOpAuthenticationManager;
import model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import service.CryptService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public final String HEADER_SECURITY_TOKEN = "My-Rest-Token";

    private AuthenticationManager authenticationManager;

    @Autowired
    private CryptService cryptService;

    public CustomTokenAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        this.authenticationManager = authenticationManager;
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(new NoOpAuthenticationManager());
        setAuthenticationSuccessHandler(new TokenSimpleUrlAuthenticationSuccessHandler());
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(HEADER_SECURITY_TOKEN);
        Authentication userAuthenticationToken = parseToken(token);
        if (userAuthenticationToken == null) {
            throw new AuthenticationServiceException("here we throw some exception or text");
        }
        return userAuthenticationToken;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    private Authentication parseToken(String tokenString) {
        try {
            String encryptedToken = cryptService.decrypt(tokenString);
            Token token = new ObjectMapper().readValue(encryptedToken, Token.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(token.getUsername(), token.getPassword()));
        } catch (Exception e) {
            return null;
        }
    }
}

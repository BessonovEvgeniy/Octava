package business.config;

import config.AppInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class BusinessSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    private AccessDeniedHandler customAccessDeniedExceptionHandler;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        List<String> profiles = Arrays.asList(context.getEnvironment().getActiveProfiles());
        if (profiles.contains(AppInitializer.PROD_PROFILE_MODE)) {
            auth.authenticationProvider(authenticationProvider());
        } else if (profiles.contains(AppInitializer.DEV_PROFILE_MODE)) {
            auth.inMemoryAuthentication().passwordEncoder(getPasswordEncoder())
                    .withUser("admin").password("nimda");
        } else {
            throw new RuntimeException(
                    "'" + AppInitializer.PROD_PROFILE_MODE +
                    "' or '" + AppInitializer.DEV_PROFILE_MODE  +
                            "' profile should be an active");
        }
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/login*").anonymous().
                anyRequest().authenticated().
                and().
                formLogin().
                and().
                exceptionHandling().
                accessDeniedHandler(customAccessDeniedExceptionHandler).
                accessDeniedPage("/login");

    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    public static class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {}
}

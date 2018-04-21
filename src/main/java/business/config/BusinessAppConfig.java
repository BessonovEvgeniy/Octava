package business.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

@Configuration
public class BusinessAppConfig {

    public static Servlet createDispatcherServlet(AnnotationConfigWebApplicationContext context) {
        context.register(BusinessMvcConfig.class);
        context.register(BusinessHibernateConfig.class);
        context.register(BusinessSecurityConfig.class);
        return new DispatcherServlet(context);
    }
}

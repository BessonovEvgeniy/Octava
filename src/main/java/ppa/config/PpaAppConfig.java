package ppa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

@Configuration
public class PpaAppConfig {

    public static Servlet createDispatcherServlet(AnnotationConfigWebApplicationContext context) {
        context.register(PpaMvcConfig.class);
        return new DispatcherServlet(context);
    }
}

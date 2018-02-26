package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {

    @Override
    @Autowired
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context = getContext();
        container.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(context));

        if (dispatcher == null) {
            System.out.println("Servlet is already added");
        } else {
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping("/");
        }
    }

    public AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MvcConfiguration.class);
        context.register(HibernateConfiguration.class);
        return context;
    }

    @Bean(name = "propertyConfigurer")
    public PropertySourcesPlaceholderConfigurer getPropertyPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer placeholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("common.properties"));
        placeholderConfigurer.setLocation(new ClassPathResource("amazon.S3Storage.properties"));
        placeholderConfigurer.setLocation(new ClassPathResource("local.storage.properties"));
        placeholderConfigurer.setLocation(new ClassPathResource("log4j.properties"));
        placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return placeholderConfigurer;
    }
}
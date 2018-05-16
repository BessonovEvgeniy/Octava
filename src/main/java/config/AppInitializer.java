package config;

import business.config.BusinessAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ppa.config.PpaAppConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {

    @Override
    @Autowired
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context = getContext();
        container.addListener(new ContextLoaderListener(context));

        container.setInitParameter("spring.profiles.active", "dev"); //Workaround for NamingException
        container.setInitParameter("spring.profiles.default", "dev"); //Workaround for NamingException
        container.setInitParameter("spring.liveBeansView.mbeanDomain", "dev"); //Workaround for NamingException

        ServletRegistration.Dynamic mainDispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(context));
        ServletRegistration.Dynamic businessDispatcher =
                container.addServlet("businessDispatcher", BusinessAppConfig.createDispatcherServlet(context));
        ServletRegistration.Dynamic ppaDispatcher =
                container.addServlet("ppaDispatcher", PpaAppConfig.createDispatcherServlet(context));

        initDispatcher(mainDispatcher, 1, "/");
        initDispatcher(businessDispatcher, 2, "/business");
        initDispatcher(ppaDispatcher, 3, "/ppa");
    }

    private void initDispatcher(ServletRegistration.Dynamic dispatcher, int loadOnStartUp, String mapping) {
        if (dispatcher == null) {
            System.out.println("Servlet" + dispatcher.getName() + " is already added");
        } else {
            dispatcher.setLoadOnStartup(loadOnStartUp);
            dispatcher.addMapping(mapping);
        }
    }

    public AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MvcConfiguration.class);
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

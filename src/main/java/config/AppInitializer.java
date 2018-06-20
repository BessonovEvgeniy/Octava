package config;

import business.config.BusinessAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Order(value = 1)
public class AppInitializer implements WebApplicationInitializer {

    public static final boolean DEV_MODE = true;
    public static final String PROD_PROFILE_MODE = "production";
    public static final String DEV_PROFILE_MODE = "dev";
    public static final String PROFILE_MODE = (DEV_MODE) ? DEV_PROFILE_MODE : PROD_PROFILE_MODE;

    @Override
    @Autowired
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context = getContext();
        container.addListener(new ContextLoaderListener(context));

        container.setInitParameter("spring.liveBeansView.mbeanDomain", PROFILE_MODE); //Workaround for NamingException

//        ServletRegistration.Dynamic mainDispatcher =
//                container.addServlet("dispatcher", new DispatcherServlet(context));
        ServletRegistration.Dynamic businessDispatcher =
                container.addServlet("businessDispatcher", BusinessAppConfig.createDispatcherServlet(context));
//        ServletRegistration.Dynamic ppaDispatcher =
//                container.addServlet("ppaDispatcher", PpaAppConfig.createDispatcherServlet(context));

        initDispatcher(businessDispatcher, 1, "/");
//        initDispatcher(mainDispatcher, 2, "/main/*");
//        initDispatcher(ppaDispatcher, 3, "/ppa");
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
        context.getEnvironment().setActiveProfiles(PROFILE_MODE);
        context.getEnvironment().setDefaultProfiles(PROFILE_MODE);
        context.register(MvcConfiguration.class);
        return context;
    }
}

//package config;
//
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import javax.servlet.Servlet;
//
//public class BusinessAppConfig {
//
//    public static Servlet createDispatcherServlet(AnnotationConfigWebApplicationContext context) {
//        context.register(BusinessSecurityConfig.class, BusinessMvcConfig.class, BusinessHibernateConfig.class);
//        return new DispatcherServlet(context);
//    }
//}

package octava.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan
public class MvcConfiguration extends WebMvcConfigurationSupport {
}

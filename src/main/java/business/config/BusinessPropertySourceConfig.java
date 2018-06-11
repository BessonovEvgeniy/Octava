package business.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BusinessPropertySourceConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        initProperties();
    }

    private void initProperties() {
        ConfigurableEnvironment env = (ConfigurableEnvironment) applicationContext.getEnvironment();
        MutablePropertySources propertySources = env.getPropertySources();
        Map properties = new HashMap<>();
        properties.put("accessDeniedPage","/");
        propertySources.addLast(new MapPropertySource("securityProperties", properties));
    }
}

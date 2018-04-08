package ppa.config;

import ppa.config.injector.FixedThreadPoolInjector;
import ppa.config.injector.LogInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ppa.service.StorageService;
import ppa.service.impl.storage.LocalStorage;

@Configuration
public class PpaBeanConfiguration {

    @Bean(name = "Storage")
    public StorageService storageBean() {
        return new LocalStorage();
    }

    @Bean(name = "logInjector")
    public LogInjector logInjector() {
        return new LogInjector();
    }

    @Bean(name = "threadPoolInjector")
    public FixedThreadPoolInjector fixedThreadPoolInjector() {
        return new FixedThreadPoolInjector();
    }
}

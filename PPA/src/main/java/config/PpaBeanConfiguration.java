package config;

import config.injector.FixedThreadPoolInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PpaBeanConfiguration {
//
//    @Bean(name = "Storage")
//    public StorageService storageBean() {
//        return new LocalStorage();
//    }


    @Bean(name = "threadPoolInjector")
    public FixedThreadPoolInjector fixedThreadPoolInjector() {
        return new FixedThreadPoolInjector();
    }
}

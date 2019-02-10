package config;

import service.StorageService;
import service.impl.storage.LocalStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BusinessBeanConfig {

    @Bean(name = "Storage")
    public StorageService storageBean() {
        return new LocalStorage();
    }
}

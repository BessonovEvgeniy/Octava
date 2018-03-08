package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ppa.service.StorageService;
import ppa.service.impl.storage.LocalStorage;

@Configuration
public class SeparateBeanConfiguration {

    @Bean(name = "Storage")
    public StorageService storageBean() {
        return new LocalStorage();
    }
}

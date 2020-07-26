package octava.config;

import octava.config.injector.FixedThreadPoolInjector;
import octava.dao.BaseRepository;
import octava.dao.impl.BaseRepositoryImpl;
import octava.model.BaseModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;

@Configuration
public class PpaBeanConfiguration {

    @Bean(name = "storageWatchService")
    public WatchService createWatchService() throws IOException {
        return FileSystems.getDefault().newWatchService();
    }

    @Bean(name = "threadPoolInjector")
    public FixedThreadPoolInjector fixedThreadPoolInjector() {
        return new FixedThreadPoolInjector();
    }

    @Bean(name = "baseRepository")
    public BaseRepository<BaseModel> createDefaultBaseRepository() {
        return new BaseRepositoryImpl<BaseModel>(){};
    }
}

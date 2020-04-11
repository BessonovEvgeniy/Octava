package octava.config;

import octava.config.injector.FixedThreadPoolInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.*;

import static octava.consts.PPAConstants.Properties.RINEX_FOLDER;

@Configuration
@PropertySource("classpath:ppa.storage.properties")
public class PpaBeanConfiguration {

    @Resource
    private Environment environment;

    @Bean(name = "storageWatchService")
    public WatchService createWatchService() throws IOException {
        return FileSystems.getDefault().newWatchService();
    }

    @Bean(name = "threadPoolInjector")
    public FixedThreadPoolInjector fixedThreadPoolInjector() {
        return new FixedThreadPoolInjector();
    }
}

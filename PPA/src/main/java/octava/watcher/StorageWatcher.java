package octava.watcher;

import octava.facade.PPAFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static octava.consts.PPAConstants.Properties.RINEX_FOLDER;

@Component
public class StorageWatcher implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(StorageWatcher.class);

    private static final WatchEvent.Kind<Path>[] WATCH_EVENTS_KINDS = new WatchEvent.Kind[] {StandardWatchEventKinds.ENTRY_CREATE};

    private static final Map<WatchKey, Path> KEY_PATH_MAP = new HashMap<>();

    @Resource
    private PPAFacade ppaFacade;

    @Resource
    private WatchService storageWatchService;

    @Resource
    private Environment environment;

    @Override
    public void run(ApplicationArguments args) {
        try {
            registerDir(Paths.get(environment.getProperty(RINEX_FOLDER)), storageWatchService);

            while (true) {
                final WatchKey key = storageWatchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE && event.context() instanceof Path) {
                        final String fullPath = environment.getProperty(RINEX_FOLDER) + event.context().toString();
                        final File file = new File(fullPath);

                        if (file.isDirectory()) {
                            registerDir(file.toPath(), storageWatchService);
                        } else {
                            ppaFacade.process(file);
                        }
                    }
                }
                if (!key.reset()) {
                    KEY_PATH_MAP.remove(key);
                }
                if (KEY_PATH_MAP.isEmpty()) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            LOG.error("StorageWatcher has been interrupted. No new files will be detected and processed.");
        }
    }

    private static void registerDir(Path path, WatchService watchService) {

        if (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            return;
        }
        try {
            LOG.info("registering: " + path);
            final WatchKey key = path.register(watchService, WATCH_EVENTS_KINDS);
            KEY_PATH_MAP.putIfAbsent(key, path);
            Arrays.stream(path.toFile().listFiles()).forEach(f -> registerDir(f.toPath(), watchService));
        } catch (IOException e) {
            LOG.error(MessageFormat.format("Can not register file watcher for {0}", path), e);
        }
    }
}

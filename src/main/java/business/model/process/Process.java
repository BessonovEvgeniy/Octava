package business.model.process;

import business.model.user.User;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public @Data class Process {

    private User user;

    private List<File> files = new ArrayList<>();

    private Logger log;

    public Process(){}

    public Process(String fileName) {
        files.add(new File(fileName));
    }

    public Process(List<String> fileNames) {
        fileNames.forEach(fileName -> files.add(new File(fileName)));
    }

    public int getNumberOfFiles() {
        return CollectionUtils.isEmpty(files) ? 0 : files.size();
    }
}

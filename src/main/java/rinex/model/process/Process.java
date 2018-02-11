package rinex.model.process;

import lombok.Data;
import rinex.model.user.User;

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
}

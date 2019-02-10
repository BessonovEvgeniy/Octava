package dto;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public @Data class ProjectDto {

    private String projectName;

    private String status;

    private List<File> files = new ArrayList<>();

    public ProjectDto(){}

    public ProjectDto(String fileName) {
        files.add(new File(fileName));
    }

    public ProjectDto(List<String> fileNames) {
        fileNames.forEach(fileName -> files.add(new File(fileName)));
    }

    public int getNumberOfFiles() {
        return CollectionUtils.isEmpty(files) ? 0 : files.size();
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}

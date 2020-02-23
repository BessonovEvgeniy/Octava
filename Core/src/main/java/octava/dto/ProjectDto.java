package octava.dto;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto {

    @NotNull
    @Size(min=4, max=18)
    private String name;

    private String status;

    private LocalDateTime created;

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

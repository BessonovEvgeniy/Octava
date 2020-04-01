package octava.dto;

import lombok.Data;
import octava.model.media.MediaModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

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

    private UserDto createdBy;

    private String status;

    private LocalDateTime created;

    private List<MultipartFile> files = new ArrayList<>();

    private List<MediaDto> rinexFiles = new ArrayList<>();

    public ProjectDto(){}

    public int getNumberOfFiles() {
        return CollectionUtils.isEmpty(files) ? 0 : files.size();
    }
}

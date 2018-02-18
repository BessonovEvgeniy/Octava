package business.model.project;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import business.model.BaseModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
public @Data class Project extends BaseModel {

    @Length(min = 4, max = 30)
    @NotNull
    private String name;

    public Project(){}

    public Project(String name){
        this.name = name;
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID")
//    private User createdBy;

    @Column
    private LocalDateTime created = LocalDateTime.now();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "RINEX_FILES")
    @MapKeyColumn(name = "LINK_TO_FILE")
    @Column(name = "FILENAME")
    private Map<String, String> rinexFiles = new HashMap<>();

    public void addRinexFiles(Map<String, String> rinexFiles) {
        this.rinexFiles.putAll(rinexFiles);
    }
}

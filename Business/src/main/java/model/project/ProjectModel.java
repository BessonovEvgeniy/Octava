package model.project;

import model.BaseModel;
import lombok.Data;
import model.rinex.RinexFileModel;
import model.user.User;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PROJECTS")
@Inheritance(strategy = InheritanceType.JOINED)
public @Data class ProjectModel extends BaseModel {

    @Length(min = 4, max = 30)
    @NotNull
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User createdBy;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private List<RinexFileModel> rinexFileModels;

    @Enumerated(EnumType.STRING)
    private Status status = Status.New;

    @Column
    private LocalDateTime created = LocalDateTime.now();

    public ProjectModel(){}

    public ProjectModel(String name, User user){
        this.name = name;
        createdBy = user;
    }

    public void addRinexFiles(List<RinexFileModel> rinexFiles) {
        this.rinexFileModels.addAll(rinexFiles);
    }

    public void addRinexFile(RinexFileModel rinexFile) {
        this.rinexFileModels.add(rinexFile);
    }

    public enum Status {
        New, Ready, Processing, Processed
    }
}

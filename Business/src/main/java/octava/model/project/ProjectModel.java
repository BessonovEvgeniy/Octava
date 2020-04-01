package octava.model.project;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.rinex.RinexFileMediaModel;
import octava.model.user.UserPrincipal;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PROJECTS")
@Inheritance(strategy = InheritanceType.JOINED)
public @Data class ProjectModel extends BaseModel {

    @Length(min = 4, max = 30)
    @NotNull
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @CreatedBy
    @JoinColumn(name = "USER_ID")
    private UserPrincipal createdBy;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID")
    private List<RinexFileMediaModel> rinexFileMediaModels;

    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    public void addRinexFiles(List<RinexFileMediaModel> rinexFiles) {
        this.rinexFileMediaModels.addAll(rinexFiles);
    }

    public void addRinexFile(RinexFileMediaModel rinexFile) {
        this.rinexFileMediaModels.add(rinexFile);
    }

    public enum Status {
        NEW, READY, PROCESSING, PROCESSED
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

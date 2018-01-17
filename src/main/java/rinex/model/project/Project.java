package rinex.model.project;

import lombok.Data;
import rinex.model.BaseModel;
import rinex.model.user.User;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public @Data class Project extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User createdBy;

    private LocalDateTime created;
}

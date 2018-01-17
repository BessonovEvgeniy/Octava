package rinex.model.user;

import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
public @Data class Role extends BaseModel{

    private String role;
}

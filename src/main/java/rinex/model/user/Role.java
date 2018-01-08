package rinex.model.user;

import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
public @Data class Role extends BaseModel{

    String role;
}

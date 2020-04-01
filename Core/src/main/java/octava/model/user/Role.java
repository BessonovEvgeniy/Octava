package octava.model.user;

import lombok.Data;
import octava.model.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
public @Data class Role extends BaseModel {

    private String role;
}

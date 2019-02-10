package model.user;

import lombok.Data;
import model.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
public @Data class Role extends BaseModel{

    private String role;
}

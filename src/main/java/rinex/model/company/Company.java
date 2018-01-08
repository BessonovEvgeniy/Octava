package rinex.model.company;

import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "COMPANY")
public @Data class Company extends BaseModel{

//    @OneToMany(mappedBy = "company")
//    private Set<User> user;
}

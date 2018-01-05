package rinex.model.company;

import lombok.Data;
import rinex.model.user.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "COMPANY")
public @Data class Company {

    @OneToMany(mappedBy = "company")
    private Set<User> user;
}

package business.model.user;

import lombok.Data;
import lombok.NonNull;
import business.model.BaseModel;
import business.model.company.Company;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
public @Data class User extends BaseModel implements Serializable {

    @NonNull
    @Column(nullable = false)
    private String name;

    private String phone;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_COMPANY",
            joinColumns =
                @JoinColumn(name = "USER_ID"),
            inverseJoinColumns =
                @JoinColumn(nullable = false)
    )

    private Company company;


}

package business.model.user;

import business.model.BaseModel;
import business.model.company.Company;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public @Data class User extends BaseModel {

    User(){}

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    private String phone;

    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USER_COMPANY",
            joinColumns =
                @JoinColumn(name = "USER_ID"),
            inverseJoinColumns =
                @JoinColumn(nullable = false)
    )
    private Company company;

    private UserPrincipal principal;
}

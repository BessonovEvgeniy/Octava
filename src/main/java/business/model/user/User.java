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
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @Column(nullable = false, unique = true, name = "LOGIN")
    private String login;

    private String password;

    @Transient
    private String confirmPassword;

    private boolean enabled;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

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

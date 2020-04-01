package octava.model.user;

import lombok.Data;
import lombok.NonNull;
import octava.model.BaseModel;
import octava.model.company.Company;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "USERS")
@Data
public class UserPrincipal extends BaseModel implements UserDetails {

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @Column(nullable = false, unique = true, name = "LOGIN")
    private String login;

    private String password;

    @Transient
    private String confirmPassword;

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

    private boolean enabled;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    public UserPrincipal() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getRole()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

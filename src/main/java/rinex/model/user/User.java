package rinex.model.user;

import lombok.Data;
import lombok.NonNull;
import rinex.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "USERS")
public @Data class User extends BaseModel implements Serializable {

    @NonNull
    @Column(nullable = false)
    private String name;

    private String phone;

    private String email;

//    @OneToOne
//    private Role role;

//    private UserAccessLevel userAccessLevel;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "USER_COMPANY",
//            joinColumns =
//                @JoinColumn(name = "USER_ID"),
//            inverseJoinColumns =
//                @JoinColumn(nullable = false)
//    )
//    private Company company;
}

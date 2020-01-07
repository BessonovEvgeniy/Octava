package octava.model.company;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.contact.Address;
import octava.model.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COMPANIES")
public @Data class Company extends BaseModel{

    private String name;

    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private Set<Department> departments = new HashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street",
            column = @Column(name = "LEGAL_ADDRESS")),
            @AttributeOverride(name = "zipCode",
            column = @Column(name = "LEGAL_ZIPCODE")),
            @AttributeOverride(name = "city",
            column = @Column(name = "LEGAL_CITY"))
    })
    private Address legalAddress;
}

package rinex.model.company;


import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "DEPARTMENTS")
public @Data class Department extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "COMPANY_DEPARTMENTS",
            joinColumns =
            @JoinColumn(name = "COMPANY_ID"),
            inverseJoinColumns =
            @JoinColumn(nullable = false)
    )
    private Company company;
}

package model.company;


import model.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "DEPARTMENTS")
public @Data class Department extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "COMPANY_DEPARTMENT",
            joinColumns =
            @JoinColumn(name = "COMPANY_ID"),
            inverseJoinColumns =
            @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    )
    private Company company;

    @Transient //TODO CREATE INTERNAL COLUMN
    private Department mainDepartment;

    @Transient //TODO CREATE INTERNAL COLUMN
    private List<Department> subDepartments;

    private String name;
}

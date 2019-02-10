package model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(generator = "BASE_GENERATOR")
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                (o != null && getClass() == o.getClass()) ||
                id.equals(((BaseModel) o).id);
    }

    @Override
    public int hashCode() {
        return 31 + (id == null ? 0 : id.hashCode());
    }
}
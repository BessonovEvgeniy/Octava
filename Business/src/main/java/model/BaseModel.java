package model;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
public @Data abstract class BaseModel {

    @Id
    @GeneratedValue
    private Long id;

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
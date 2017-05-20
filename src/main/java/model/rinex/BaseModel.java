package model.rinex;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseModel{

    @Id
    @GeneratedValue
    protected Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        BaseModel baseModel = (BaseModel) o;
        return id.equals(baseModel.id);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + (id == null ? 0 : id.hashCode());
        return hash;
    }
}
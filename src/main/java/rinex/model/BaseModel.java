package rinex.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue
    protected Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o || (o != null && getClass() == o.getClass())) {
            return true;
        } else {
            return id.equals(((BaseModel) o).id);
        }
    }

    @Override
    public int hashCode() {
        return 1 * 31 + (id == null ? 0 : id.hashCode());
    }
}
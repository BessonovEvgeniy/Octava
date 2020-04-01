package octava.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseModel {

    @Id
    @GeneratedValue(generator = "BASE_GENERATOR")
    private Long id;

    @Column
    @CreatedDate
    protected LocalDateTime created;

    @Column
    @LastModifiedDate
    protected LocalDateTime updated;


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
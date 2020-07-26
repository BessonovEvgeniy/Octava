package octava.model;

import lombok.Data;
import octava.model.rinex.RinexFileMediaModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "PREPROCESSING")
public class PreProcessingModel extends BaseModel {

    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RinexFileMediaModel> rinexFiles;

}

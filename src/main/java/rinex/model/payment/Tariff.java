package rinex.model.payment;

import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.Entity;

@Entity
public @Data class Tariff extends BaseModel {

    private String name;


}

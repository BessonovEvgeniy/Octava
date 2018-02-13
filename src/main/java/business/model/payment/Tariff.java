package business.model.payment;

import lombok.Data;
import business.model.BaseModel;

import javax.persistence.Entity;

@Entity
public @Data class Tariff extends BaseModel {

    private String name;


}

package octava.model.payment;

import lombok.Data;
import octava.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public @Data class Tariff extends BaseModel {

    @Column(nullable = false)
    private String name;

//    @Embedded
//    private MonetaryAmount tax;
}

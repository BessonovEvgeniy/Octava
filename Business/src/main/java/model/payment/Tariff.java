package model.payment;

import model.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public @Data class Tariff extends BaseModel {

    @Column(nullable = false)
    private String name;

//    @Embedded
//    private MonetaryAmount tax;
}

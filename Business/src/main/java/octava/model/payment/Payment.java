package octava.model.payment;

import octava.model.BaseModel;

import javax.persistence.Embedded;

public class Payment extends BaseModel {

    @Embedded
    private MonetaryAmount monetaryAmount;
}

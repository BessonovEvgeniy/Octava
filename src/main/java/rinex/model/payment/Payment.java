package rinex.model.payment;

import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.Embedded;

public @Data class Payment extends BaseModel {

    @Embedded
    private MonetaryAmount monetaryAmount;
}

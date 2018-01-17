package rinex.model.payment;

import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

@Embeddable
public @Data class MonetaryAmount extends BaseModel implements Serializable {

    protected BigDecimal value;

    protected Currency currency;

    protected MonetaryAmount() {}

    public MonetaryAmount(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 29*result + currency.hashCode();
        return result;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof MonetaryAmount)) {
            return false;
        }

        final MonetaryAmount monetaryAmount = (MonetaryAmount) o;

        if (!value.equals(monetaryAmount.getValue())) {
            return false;
        }
        if (!currency.equals(monetaryAmount.getCurrency())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return value.toString() + " " + currency.toString();
    }

    public static MonetaryAmount fromString(String str) {
        String[] split = str.split(" ");
        return new MonetaryAmount(new BigDecimal(split[0]), Currency.getInstance(split[1]));
    }

}

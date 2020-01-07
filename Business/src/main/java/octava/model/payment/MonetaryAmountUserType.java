package octava.model.payment;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.Properties;

public class MonetaryAmountUserType implements CompositeUserType, DynamicParameterizedType {

    protected Currency convertTo;

    @Override
    public void setParameterValues(Properties properties) {
        String convertToParameter = properties.getProperty("convertTo");
        this.convertTo = Currency.getInstance(
                convertToParameter != null ? convertToParameter : "USD"
        );
    }

    @Override
    public Class returnedClass() {
        return MonetaryAmount.class;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public Serializable disassemble(Object o, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException {
        return o.toString();
    }

    @Override
    public Object assemble(Serializable serializable, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return MonetaryAmount.fromString((String) serializable);
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return o == o1 || !(o != null || o1 != null) && o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object replace(Object o, Object o1, SharedSessionContractImplementor sharedSessionContractImplementor, Object o2) throws HibernateException {
        return o;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        BigDecimal amount = resultSet.getBigDecimal(names[0]);
        if (resultSet.wasNull()) {
            return null;
        }
        Currency currency = Currency.getInstance(resultSet.getString(names[1]));
        return new MonetaryAmount(amount, currency);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(index, StandardBasicTypes.BIG_DECIMAL.sqlType());
            preparedStatement.setNull(index+1, StandardBasicTypes.CURRENCY.sqlType());
        } else {
            MonetaryAmount amount = (MonetaryAmount) o;
            MonetaryAmount amountDb = convert(amount, convertTo);
            preparedStatement.setBigDecimal(index, amountDb.getValue());
            preparedStatement.setString(index + 1, convertTo.getCurrencyCode());
        }
    }

    protected MonetaryAmount convert(MonetaryAmount amount, Currency convertTo) {
        return new MonetaryAmount(amount.getValue().multiply(new BigDecimal(2)), convertTo); //TODO Implement convertation
    }

    @Override
    public String[] getPropertyNames() {
        return new String[]{"value", "currency"};
    }

    @Override
    public Type[] getPropertyTypes() {
        return new Type[]{
                StandardBasicTypes.BIG_DECIMAL,
                StandardBasicTypes.CURRENCY
        };
    }

    @Override
    public Object getPropertyValue(Object component, int index) throws HibernateException {
        MonetaryAmount monetaryAmount = (MonetaryAmount) component;
        if (index == 0) {
            return monetaryAmount.getValue();
        } else {
            return monetaryAmount.getCurrency();
        }
    }

    @Override
    public void setPropertyValue(Object o, int i, Object o1) throws HibernateException {
        //TODO Implement logic
    }
}

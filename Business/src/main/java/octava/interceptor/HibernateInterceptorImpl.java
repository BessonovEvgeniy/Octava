package octava.interceptor;

import octava.strategy.HibernateInterceptorStrategy;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import javax.annotation.Resource;
import java.io.Serializable;

import static java.util.Objects.nonNull;

public class HibernateInterceptorImpl extends EmptyInterceptor {

    @Resource
    private HibernateInterceptorStrategy hibernateInterceptorStrategy;

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        boolean entityChanged = false;

        final EmptyInterceptor interceptor = hibernateInterceptorStrategy.getStrategy(entity);

        if (nonNull(interceptor)) {
            entityChanged = interceptor.onSave(entity, id, state, propertyNames, types);
        }

        return entityChanged;
    }
}

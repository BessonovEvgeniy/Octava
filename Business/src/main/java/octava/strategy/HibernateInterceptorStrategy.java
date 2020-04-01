package octava.strategy;

import org.hibernate.EmptyInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component("hibernateInterceptorStrategy")
public class HibernateInterceptorStrategy {

    @Resource
    private Map<Class, EmptyInterceptor> hibernateInterceptorStrategies;

    public EmptyInterceptor getStrategy(Object object) {
        for (Class clazz : hibernateInterceptorStrategies.keySet()) {
            if (object.getClass() == clazz) {
                return hibernateInterceptorStrategies.get(clazz);
            }
        }
        return null;
    }
}

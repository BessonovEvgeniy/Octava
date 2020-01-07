package octava.config.injector;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolInjector implements BeanPostProcessor {

    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);

            if (field.getAnnotation(InjectThreadPool.class) != null) {
                field.set(bean, executorService);
            }
        });

        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}

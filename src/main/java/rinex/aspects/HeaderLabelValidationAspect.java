package rinex.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HeaderLabelValidationAspect implements AspectExecutor {

    @Override
    @Pointcut("execution(* rinex.model.observations.header.AbstractHeaderLabel+.*(..))")
    public void pointcut(){}

    @Override
    @Before("pointcut()")
    public Object executeAspect(JoinPoint joinPoint) throws Throwable {
        Object[] params = joinPoint.getArgs();
        if (params[0] instanceof String) {
            String line = (String) params[0];
        }
        return new Object();
    }

}

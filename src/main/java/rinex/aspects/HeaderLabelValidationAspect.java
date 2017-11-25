package rinex.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rinex.service.impl.observations.rinex.impl.HeaderLabelValidation;

@Aspect
@Component
public class HeaderLabelValidationAspect implements AspectExecutor {

    @Autowired
    private HeaderLabelValidation validator;

    @Override
    @Pointcut("execution(* rinex.model.observations.header.HeaderLabel+.*(..))")
    public void pointcut(){}

    @Override
    @After("pointcut()")
    public void executeAspect(JoinPoint joinPoint) throws Throwable {
        validator.validate(joinPoint.getTarget());
    }

}

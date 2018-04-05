package ppa.aspect;

import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ppa.service.impl.rinex.HeaderLabelValidation;

@Aspect
@Component
public class HeaderLabelValidationAspect implements AspectExecutor {

    @Autowired
    private HeaderLabelValidation validator;

    @Override
    @Pointcut("execution(* ppa.service.HeaderLabelParserService+.*(..))")
    public void pointcut(){}

    @Override
    @Before("pointcut()")
    public void executeAspectBefore(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (ArrayUtils.isNotEmpty(args)) {
            validator.validateLineLength(args[0]);
        }
    }

    @Override
    @After("pointcut()")
    public void executeAspectAfter(JoinPoint joinPoint) throws Throwable {
        validator.validate(joinPoint.getTarget());
    }

}

package rinex.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class HeaderLabelValidationAspect {

    @Pointcut("execution(* rinex.service.HeaderLabel.parse(String))")
    void parseLinePoincut(){}

    @Before("parseLinePoincut()")
    public void validate(JoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        if (params[0] instanceof String) {
            String line = (String) params[0];
        }
    }

}

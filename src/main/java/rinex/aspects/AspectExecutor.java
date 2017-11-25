package rinex.aspects;

import org.aspectj.lang.JoinPoint;

public interface AspectExecutor {

    void pointcut();

    void executeAspect(JoinPoint joinPoint) throws Throwable;
}

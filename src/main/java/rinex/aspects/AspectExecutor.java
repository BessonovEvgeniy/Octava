package rinex.aspects;

import org.aspectj.lang.JoinPoint;

public interface AspectExecutor {

    void pointcut();

    Object executeAspect(JoinPoint joinPoint) throws Throwable;
}

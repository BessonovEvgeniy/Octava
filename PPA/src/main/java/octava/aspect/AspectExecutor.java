package octava.aspect;

import org.aspectj.lang.JoinPoint;

public interface AspectExecutor {

    void pointcut();

    void executeAspectAfter(JoinPoint joinPoint) throws Throwable;

    void executeAspectBefore(JoinPoint joinPoint) throws Throwable;
}

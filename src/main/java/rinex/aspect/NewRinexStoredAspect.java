package rinex.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rinex.controller.processor.ProcessController;
import rinex.model.process.Process;

@Aspect
@Component
public class NewRinexStoredAspect{

    @Autowired
    ProcessController processor;

    @Pointcut("execution(* rinex.service.StorageService.store(..))")
    public void pointcut() {}

    @AfterReturning(value = "pointcut()", returning = "file")
    public void executeAspectAfter(String file) throws Throwable {
        processor.process(new Process(file));
    }
}

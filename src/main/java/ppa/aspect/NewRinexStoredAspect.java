package ppa.aspect;

import business.model.process.Process;
import config.injector.InjectLog;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ppa.controller.ProcessController;

@Aspect
@Component
public class NewRinexStoredAspect{

    @Autowired
    ProcessController processor;

    @InjectLog
    private Logger logger;

    @Pointcut("execution(* ppa.service.StorageService.store(..))")
    public void pointcut() {}

    @AfterReturning(value = "pointcut()", returning = "file")
    public void executeAspectAfter(String file) throws Throwable {
        logger.getLogger(this.getClass().getName()).info("New rinex registered. Perform calculation.");
        processor.process(new Process(file));
    }
}

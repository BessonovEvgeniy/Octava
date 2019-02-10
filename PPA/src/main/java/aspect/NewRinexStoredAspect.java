package aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NewRinexStoredAspect{

//    @Autowired
//    private ProcessController processor;
//
//    @Pointcut("execution(* ppa.service.StorageService.store(..))")
//    public void pointcut() {}
//
//    @AfterReturning(value = "pointcut()", returning = "file")
//    public void executeAspectAfter(String file) throws Throwable {
//        processor.process(new ProjectDto(file));
//    }
}

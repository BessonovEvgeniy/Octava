package octava.controller;

import octava.config.injector.InjectThreadPool;
import octava.facade.impl.PPAFacadeImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/ppa")
public class PPAController implements ProcessorState {

    @Resource
    private PPAFacadeImpl ppaFacadeImpl;

    @InjectThreadPool
    private ExecutorService executor;


//    @PostMapping(value = "/process")
//    public PpaResult process(@Valid ProjectDto projectDto, BindingResult bindingResult, HttpServletRequest request) throws Exception {
//
//        ppaFacadeImpl.process(projectDto);
//
//        return null;
//    }
}

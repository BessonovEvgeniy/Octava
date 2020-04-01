package octava.controller;

import octava.config.injector.InjectThreadPool;
import octava.dto.ProjectDto;
import octava.facade.PPAFacade;
import octava.model.PpaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/ppa")
public class PPAController implements ProcessorState {

    @Resource
    private PPAFacade ppaFacade;

    @InjectThreadPool
    private ExecutorService executor;


    @PostMapping(value = "/process")
    public PpaResult process(@Valid ProjectDto projectDto, BindingResult bindingResult, HttpServletRequest request) throws Exception {

        ppaFacade.process(projectDto);

        return null;
    }
}

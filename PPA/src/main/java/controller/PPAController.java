package controller;

import config.injector.InjectThreadPool;
import dto.ProjectDto;
import facade.PPAFacade;
import model.PpaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/ppa")
public class PPAController implements ProcessorState {

    @Autowired
    private PPAFacade ppaFacade;

    @InjectThreadPool
    private ExecutorService executor;


    @PostMapping(value = "/process")
    public PpaResult process(@Valid ProjectDto projectDto, BindingResult bindingResult, HttpServletRequest request) throws Exception {

        ppaFacade.process(projectDto);

        return null;
    }
}

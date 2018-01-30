package rinex.controller.rinex;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rinex.model.project.Project;
import rinex.service.ProjectService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProject (Model model) throws Exception {
        model.addAttribute(new Project());
        return "project/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProject (@Valid Project project, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "redirect:/create";
        }
        service.insert(project);
        return "project/uploadData";
    }
}

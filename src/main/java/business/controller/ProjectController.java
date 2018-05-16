package business.controller;


import business.model.project.Project;
import business.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/business/project")
public class ProjectController {

    private ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newProject (Model model) throws Exception {
        model.addAttribute(new Project());
        return "project/new";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProject (@Valid Project project, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return "project/new";
        }
        service.insert(project);
        return "project/uploadData";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listProject (Model model) throws Exception {
        List<Project> projects = service.getAll();
        model.addAttribute("projects",projects);
        return "project/list";
    }
}

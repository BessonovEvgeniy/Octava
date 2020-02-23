package octava.controller;


import octava.constants.Constants;
import octava.dto.ProjectDto;
import octava.facade.ProjectFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static octava.constants.Constants.Controller.Project.PROJECT;
import static octava.constants.Constants.Controller.Project.PROJECTS;

@Controller
@PreAuthorize("isAuthenticated()")
@SessionAttributes({PROJECTS})
public class ProjectController extends AbstractBusinessController {

    @Resource
    private ProjectFacade projectFacade;

    @PostMapping(value = Constants.Controller.Project.CREATE)
    public String create(Model model,
                         HttpServletRequest request,
                         @Valid @ModelAttribute(name = PROJECT) ProjectDto project,
                         BindingResult bindingResult,
                         Principal principal) {

        if (bindingResult.hasErrors()) {

            return request.getHeader("Referer").replace(request.getHeader("origin"), StringUtils.EMPTY);
        }

        final ProjectDto createdProject = projectFacade.create(project, principal);

        model.addAttribute(PROJECT, createdProject);
        return "redirect:" + Constants.Controller.Project.LIST;
    }

    @GetMapping(value = Constants.Controller.Project.LIST)
    public String list(final Model model, final Principal principal) {

        final List<ProjectDto> projects = projectFacade.projectsByUser(principal);

        model.addAttribute(PROJECT, new ProjectDto());
        model.addAttribute(PROJECTS, projects);
        return Constants.Controller.Project.LIST;
    }

    @PostMapping(value = Constants.Controller.Project.UPLOAD)
    public String uploadRinexFile(@RequestParam  MultipartFile file,
                                  HttpServletRequest request,
                                  Principal principal) throws Exception {

        getStorageService().store(file);

        return Constants.Controller.Project.LIST;
    }

}

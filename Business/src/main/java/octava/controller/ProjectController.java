package octava.controller;


import octava.constants.Constants;
import octava.dto.ProjectDto;
import octava.facade.ProjectFacade;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class ProjectController extends AbstractBusinessController {

    @Resource
    private ProjectFacade projectFacade;

    @PostMapping(value = Constants.Controller.Project.CREATE)
    public String create(Model model,
                         @RequestParam String projectName,
                         Principal principal) {

        final ProjectDto projectDto = projectFacade.create(projectName, principal.getName());

        model.asMap().clear();
        model.addAttribute("project", projectDto);
        return "project/uploadData";
    }


    @GetMapping(value = Constants.Controller.Project.LIST)
    public String list(Model model, Principal principal) {

        final List<ProjectDto> projectDtoList = projectFacade.projectsByUser(principal);

        model.asMap().clear();
        model.addAttribute("projects", projectDtoList);
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

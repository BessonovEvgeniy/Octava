package controller;


import dto.ProjectDto;
import dto.RinexFileDto;
import facade.ProjectFacade;
import model.project.ProjectModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import service.ProjectService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = Constants.Controller.Project.PREFIX)
@PreAuthorize("isAuthenticated()")
public class ProjectController extends AbstractBusinessController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectFacade projectFacade;


    @PostMapping(value = Constants.Controller.Project.CREATE)
    public String create(Model model,
                         @RequestParam String projectName,
                         Principal principal) throws Exception {

        principal = resolvePrincipal(principal);

        ProjectModel projectModel = projectFacade.create(projectName, principal.getName());
        ProjectDto projectDto = projectFacade.convert(projectModel);

        model.asMap().clear();
        model.addAttribute("project", projectDto);
        return "project/uploadData";
    }


    @GetMapping(value = Constants.Controller.Project.LIST)
    public String list(Model model, Principal principal) throws Exception {
        List<ProjectModel> projectModels = projectService.getAllByUser(principal);
        List<ProjectDto> projectDtoList = projectModels.stream().map(project -> projectFacade.convert(project)).collect(Collectors.toList());

        model.asMap().clear();
        model.addAttribute("projects", projectDtoList);
        return "project/list";
    }

    @PostMapping(value = Constants.Controller.Project.UPLOAD)
    public String uploadRinexFile(@RequestParam  MultipartFile file,
                                  HttpServletRequest request,
                                  Principal principal) throws Exception {

        HttpEntity<MultiValueMap<String, Object>> requestEntity = prepareHttpEntityForMultipartFile(file);

        RestTemplate rinexRest = new RestTemplate();

        ResponseEntity<RinexFileDto> storedFiles = rinexRest.postForEntity(getURLBase(request) + Constants.Controller.RestApi.Rinex.UPLOAD, requestEntity, RinexFileDto.class);

        return "project/fileUploaded";
    }
}

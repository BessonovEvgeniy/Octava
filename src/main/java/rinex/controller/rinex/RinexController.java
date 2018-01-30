package rinex.controller.rinex;


import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rinex.model.project.Project;
import rinex.service.RinexService;
import rinex.service.StorageService;

@Controller
@RequestMapping("/rinex")
public class RinexController {

    @Autowired
    private RinexService rinexService;

    @Autowired
    @Qualifier("localStorage")
    private StorageService storage;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadRinexFile (@RequestParam(required = false) Project project,
                                   @RequestParam(value = "file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws Exception {
        try {
           if (!file.isEmpty()) {
               rinexService.validateRinex(file);
               storage.store(file, project);
           }
        } catch (FileUploadException e) {
            return "";
        }
        return "redirect:project/list";
    }
}

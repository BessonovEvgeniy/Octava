package business.controller;


import business.model.project.Project;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ppa.service.RinexService;
import ppa.service.StorageService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/rinex")
public class RinexController {

    private RinexService rinexService;

    private StorageService storage;

    @Autowired
    public RinexController(RinexService rinexService) {
        this.rinexService = rinexService;
    }

    @Resource(name = "Storage")
    public void setStorageService(StorageService storage) {
        this.storage = storage;
    }

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
        return "redirect:/project/list";
    }
}

package business.controller;


import business.model.process.Process;
import business.model.project.Project;
import business.service.StorageService;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
@RequestMapping("/rinex")
public class RinexController {

    private static final String ppaResourceUrl = "http://localhost:8080/ppa/process";

    private StorageService storage;

    @Resource(name = "Storage")
    public void setStorageService(StorageService storage) {
        this.storage = storage;
    }

    @PostMapping(value = "/upload")
    public String uploadRinexFile(@RequestParam(required = false) Project project,
                                  @RequestParam(value = "file") MultipartFile file,
                                  RedirectAttributes redirectAttributes) throws Exception {
        try {
            if (!file.isEmpty()) {
                String fileName = storage.store(file, project);
                RestTemplate restTemplate = new RestTemplate();
                HttpEntity<Process> request = new HttpEntity<>(new Process(fileName));
                Process process = restTemplate.postForObject(ppaResourceUrl, request, Process.class);
            }
        } catch (FileUploadException e) {
            return "";
        }
        return "redirect:/project/list";
    }
}

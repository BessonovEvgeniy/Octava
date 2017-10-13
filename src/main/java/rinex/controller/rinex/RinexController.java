package rinex.controller.rinex;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rinex.model.observations.ReceiverDataModel;
import rinex.service.Impl.observations.rinex.rinexImpl.RinexServiceImpl;
import rinex.service.RinexService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

@Controller
@javax.servlet.annotation.MultipartConfig
public class RinexController {

//    private final StorageService storageService;
//
//    @Autowired
//    public RinexController(StorageService storageService) {
//        this.storageService = storageService;
//    }

    @Autowired
    private RinexService rinexService;

    @RequestMapping({"/", "/home"})
    public String homePage(Map<String, Object> model) {
        return "index";
    }

    @RequestMapping(value = "/readRinex", method = RequestMethod.POST)
    public String uploadRinexFile (BindingResult bindingResult,
                                   @RequestParam(value = "file", required = true) MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws Exception {

        if (file.isEmpty()) {
            return "redirect:uploadStatus";
        } else {
            InputStream inputStream =  new BufferedInputStream(file.getInputStream());
            ReceiverDataModel receiverDataModel  = rinexService.readRinex(inputStream);
            return "redirect:uploadStatus";
        }
    }

    public static void main(String[] args) {
        String filename = "src/test/java/resources/38541890.16o";
        File file = new File(filename);
        try {
            RinexServiceImpl rinexService = new RinexServiceImpl();
            InputStream rinexData = new FileInputStream(filename);

            ReceiverDataModel data = rinexService.readRinex(rinexData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package rinex.controller.rinex;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rinex.model.observations.ReceiverDataModel;
import rinex.service.impl.observations.rinex.impl.RinexServiceImpl;
import rinex.service.RinexService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Controller
@RequestMapping("/rinex")
public class RinexController {

    private RinexService rinexService;

    @Autowired
    public RinexController(RinexService rinexServ) {
        rinexService = rinexServ;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String uploadRinexFile (@RequestParam(value = "file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws Exception {

        //TODO add rinex header validation and change logic to storing uploaded rinex file
        if (file.isEmpty()) {
            return "redirect:index";
        } else {
            InputStream inputStream =  new BufferedInputStream(file.getInputStream());
            ReceiverDataModel receiverDataModel  = rinexService.readRinex(inputStream);
            return "redirect:index";
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

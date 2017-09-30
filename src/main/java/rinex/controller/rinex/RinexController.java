package rinex.controller.rinex;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rinex.model.observations.ReceiverDataModel;
import rinex.service.Impl.observations.rinex.rinexImpl.RinexServiceImpl;
import rinex.service.RinexService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

@Controller
public class RinexController {

    @Autowired
    private RinexService rinexService;

    @RequestMapping("/")
    public String homePage(Map<String, Object> model) {
        model.put("spittles", new Object());
        return "index";
    }

//    @RequestMapping("/readRinex")
//    public Response uploadRinexFile (
//            InputStream uploadedInputStream,
//            FormDataContentDisposition fileDetail) throws Exception {
//
//        ReceiverDataModel receiverDataModel  = rinexService.readRinex(uploadedInputStream);
//
//        Response response = receiverDataModel.equals(ReceiverDataModel.NULL) ? Response.serverError().build() : Response.status(200).build();
//        return response;
//    }

    public static void main(String[] args) {
//        String filename = "d:\\3 GPS data\\2013\\010\\RINEX\\ALCI0100.13O";
        String filename = "//home//ionex//1 IdeaProjects//Octava//src//test//java//resources//X07100.17o";
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

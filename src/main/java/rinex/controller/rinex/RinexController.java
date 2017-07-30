package rinex.controller.rinex;


import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import rinex.model.observations.ReceiverDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rinex.service.Impl.observations.rinex.rinexImpl.RinexServiceImpl;
import rinex.service.RinexService;

import javax.ws.rs.core.Response;
import java.io.*;
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

    @RequestMapping("/readRinex")
    public Response uploadRinexFile (
            InputStream uploadedInputStream,
            FormDataContentDisposition fileDetail) throws Exception {

        ReceiverDataModel receiverDataModel  = rinexService.readRinex(uploadedInputStream);

        Response response = receiverDataModel.equals(ReceiverDataModel.NULL) ? Response.serverError().build() : Response.status(200).build();
        return response;
    }

    public static void main(String[] args) {
        String filename = "d:\\3 GPS data\\2013\\010\\RINEX\\ALCI0100.13O";
//        String filename = "//home//ionex//1 IdeaProjects//Octava//X07100.17o";
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

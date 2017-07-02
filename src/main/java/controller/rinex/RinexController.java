package controller.rinex;


import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import model.observations.ReceiverDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.Impl.observations.rinex.rinexImpl.RinexServiceImpl;
import service.RinexService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Controller
public class RinexController {

    @Autowired
    private RinexService rinexService;

    @POST
    @Path("/readRinex")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadRinexFile (
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {

        ReceiverDataModel receiverDataModel = ReceiverDataModel.NULL;

        try {
            BufferedReader reader= new BufferedReader(new InputStreamReader(uploadedInputStream));
            receiverDataModel = rinexService.readRinex(reader);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Response response = (receiverDataModel.equals(ReceiverDataModel.NULL)) ? Response.serverError().build() : Response.status(200).build();
            return response;
        }
    }

    public static void main(String[] args) {
//        String filename = "d:\\3 GPS data\\2013\\010\\RINEX\\ALCI0100.13O";
        String filename = "//home//ionex//1 IdeaProjects//Octava//X07100.17o";
        try {
            RinexServiceImpl rinexService = new RinexServiceImpl();
            InputStream rinexData = new FileInputStream(filename);
            BufferedReader reader= new BufferedReader(new InputStreamReader(rinexData));

            ReceiverDataModel data = rinexService.readRinex(reader);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

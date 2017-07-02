package controller.rinex;


import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import model.observations.TNP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.observations.rinex.RinexService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Controller
public class ReadRinexController {

    @Autowired
    private RinexService rinexService;

    @POST
    @Path("/readRinex")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadRinexFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {

        TNP tnp = rinexService.readRinex(uploadedInputStream);

        Response response = (tnp.equals(TNP.NULL)) ? Response.serverError().build() : Response.status(200).build();
        return response;
    }
}

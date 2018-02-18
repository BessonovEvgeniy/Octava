package ppa.service.impl.rinex.impl;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ppa.exception.UnknownHeaderLabelException;
import ppa.model.observation.ReceiverDataModel;
import business.model.process.Process;
import ppa.service.RinexService;
import ppa.service.State;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RinexServiceImpl implements RinexService {


    public static final String REQUIRED_CONTENT_TYPE = "";

    private State state;

    private ReceiverDataModel data = new ReceiverDataModel();

    @Autowired
    private ReadHeaderImpl readHeader;

    @Autowired
    private ReadRinexObservationsDecorator readObservations;

    public List<ReceiverDataModel> readRinex(Process process) throws Exception {
        List<ReceiverDataModel> results = new ArrayList<>();
        for (File file : process.getFiles()) {
            InputStream inputStream = new FileInputStream(file);
            results.add(readRinex(inputStream));
        }
        return results;
    }

    private ReceiverDataModel readRinex(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return ReceiverDataModel.NULL;
        } else {
            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(inputStream))) {

                changeState(readHeader);
                state.read(reader, data);
                changeState(readObservations);
                state.read(reader, data);
            } catch (UnknownHeaderLabelException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw new Exception();
            }
            return data;
        }
    }

    private void changeState(State state){
        this.state = state;
    }

    @Override
    public void validateRinex(MultipartFile rinexFile) throws Exception {

        Matcher matcher = Pattern.compile("^*.\\d{2}o$").matcher(rinexFile.getOriginalFilename());
        if (!matcher.find()) {
            throw new FileUploadException("Illegal file name " + rinexFile.getOriginalFilename());
        }

        if (rinexFile.getContentType().equals(REQUIRED_CONTENT_TYPE)) {
            throw new FileUploadException("Incompatible content type: " + rinexFile.getContentType() + " ");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(rinexFile.getInputStream()));
        ReceiverDataModel dataModel = new ReceiverDataModel();
        readHeader.read(br, dataModel);

        System.out.println(dataModel.getRinexVersionType().getVersion());
    }
}
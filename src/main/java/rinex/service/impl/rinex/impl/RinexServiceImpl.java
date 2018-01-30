package rinex.service.impl.rinex.impl;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rinex.exception.UnknownHeaderLabelException;
import rinex.model.observation.ReceiverDataModel;
import rinex.service.RinexService;
import rinex.service.State;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RinexServiceImpl implements RinexService {

    private State state;

    private ReceiverDataModel data;

    @Autowired
    private ReadHeaderImpl readHeader;

    public ReceiverDataModel readRinex(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return ReceiverDataModel.NULL;
        } else {
            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(inputStream))) {

                changeState(readHeader);
                state.read(reader, data);
                changeState(new ReadRinexObservationsDecorator());
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

        BufferedReader br = new BufferedReader(new InputStreamReader(rinexFile.getInputStream()));
        ReceiverDataModel dataModel = new ReceiverDataModel();
        readHeader.read(br, dataModel);

        System.out.println(dataModel.getRinexVersionType().getVersion());
    }
}
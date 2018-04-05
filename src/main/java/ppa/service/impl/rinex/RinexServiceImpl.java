package ppa.service.impl.rinex;

import config.AppInitializer;
import config.HibernateConfiguration;
import config.MvcConfiguration;
import config.injector.InjectLog;
import config.injector.LogInjector;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ppa.model.observation.ReceiverDataModel;
import ppa.service.RinexService;

import javax.inject.Provider;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RinexServiceImpl implements RinexService {

    public static final String REQUIRED_CONTENT_TYPE = "";
    public static final Pattern RINEX_FILE_PATTERN = Pattern.compile("^*.\\d{2}o$");

    @InjectLog
    private Logger log;

    @Autowired
    private Provider<ReceiverDataModel> rdmProvider;

    private ReadHeaderImpl readHeader;

    private ReadRinexObservationsDecorator readObservations;

    @Autowired
    public RinexServiceImpl(ReadHeaderImpl readHeader, ReadRinexObservationsDecorator readObservations) {
        this.readHeader = readHeader;
        this.readObservations = readObservations;
    }

    public ReceiverDataModel readRinex(File file) throws Exception {
        InputStream inputStream = new FileInputStream(file);
        return readRinex(inputStream);
    }

    private ReceiverDataModel readRinex(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return ReceiverDataModel.NULL;
        } else {
            ReceiverDataModel data = rdmProvider.get();

            log.info("Rinex reading...");
            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(inputStream))) {

                readHeader.read(reader, data);
                readObservations.read(reader, data);
            }
            return data;
        }
    }

    @Override
    public void validateRinex(MultipartFile rinexFile) throws Exception {
        log.info("Rinex header validation...");
        Matcher matcher = RINEX_FILE_PATTERN.matcher(rinexFile.getOriginalFilename());
        if (!matcher.find()) {
            String msg = "Illegal file name " + rinexFile.getOriginalFilename();
            log.warn(msg);
            throw new FileUploadException(msg);
        }
        if (rinexFile.getContentType().equals(REQUIRED_CONTENT_TYPE)) {
            String msg = "Incompatible content type: " + rinexFile.getContentType() + " ";
            log.warn(msg);
            throw new FileUploadException(msg);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(rinexFile.getInputStream()));
        ReceiverDataModel dataModel = rdmProvider.get();
        readHeader.read(br, dataModel);
        log.info("Rinex header is valid.");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppInitializer.class);
        context.register(MvcConfiguration.class);
        context.register(HibernateConfiguration.class);
        context.registerBean(LogInjector.class);
        context.scan("ppa", "utils", "config");

        ReceiverDataModel rdm = context.getBean(ReceiverDataModel.class);

//        Assert.isTrue(model1 != model2, "Should be diff objects");
    }
}
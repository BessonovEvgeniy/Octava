package ppa.service.impl.rinex.impl;

import business.model.process.Process;
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
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ppa.model.observation.ReceiverDataModel;
import ppa.service.RinexService;

import javax.inject.Provider;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RinexServiceImpl implements RinexService {

    public static final String REQUIRED_CONTENT_TYPE = "";
    public static final Pattern RINEX_FILE_PATTERN = Pattern.compile("^*.\\d{2}o$");

    @Autowired
    private Provider<ReceiverDataModel> dataModelProvider;
    @InjectLog
    private Logger log;
    private ReadHeaderImpl readHeader;
    private ReadRinexObservationsDecorator readObservations;

    @Autowired
    public RinexServiceImpl(ReadHeaderImpl readHeader, ReadRinexObservationsDecorator readObservations) {
        this.readHeader = readHeader;
        this.readObservations = readObservations;
    }

    public List<ReceiverDataModel> readRinex(Process process) {
        List<ReceiverDataModel> results = new ArrayList<>();
        for (File file : process.getFiles()) {
            try {
                InputStream inputStream = new FileInputStream(file);
                results.add(readRinex(inputStream));
            } catch (Exception e) {
                log.warn("File " + file.getAbsolutePath() + " isn't processed due to some problems. File skipped.");
                e.printStackTrace();
            }
        }
        return results;
    }

    private ReceiverDataModel readRinex(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return ReceiverDataModel.NULL;
        } else {
            ReceiverDataModel data = dataModelProvider.get();

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
        ReceiverDataModel dataModel = dataModelProvider.get();
        readHeader.read(br, dataModel);
        log.info("Rinex header is valid.");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppInitializer.class);
        context.register(MvcConfiguration.class);
        context.register(HibernateConfiguration.class);
        context.registerBean(LogInjector.class);
        context.scan("ppa", "utils", "config");

        RinexServiceImpl serv1 = context.getBean(RinexServiceImpl.class);
        ReceiverDataModel model1 = serv1.dataModelProvider.get();
        ReceiverDataModel model2 = serv1.dataModelProvider.get();

        Assert.isTrue(model1 != model2, "Should be diff objects");
    }
}
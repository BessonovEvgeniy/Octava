package ppa.service.impl.rinex;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import config.AppInitializer;
import business.config.BusinessHibernateConfig;
import config.MvcConfiguration;
import ppa.config.injector.InjectLog;
import ppa.config.injector.LogInjector;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ppa.dto.EpochDto;
import ppa.model.observation.ReceiverDataModel;
import ppa.model.observation.header.impl.TypesOfObs;
import ppa.service.PreProcessRawObsService;
import ppa.service.RinexReader;
import ppa.service.impl.observations.header.TypesOfObsParserServiceImpl;
import utils.date.DateUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("2.11")
public class ReadRinexObservationsV211Impl extends AbstractReadRinexObservations implements RinexReader {

    public final static String EPOCH_TIME_REGEXP = "(" + Strings.repeat("\\s*\\d{1,2}",5) + "\\s*\\d{1,2}.\\d{7})\\s{1,2}(\\d{1})\\s{1,2}(\\d{1,2})(.*)";
    public final static Pattern EPOCH_TIME_PATTERN = Pattern.compile(EPOCH_TIME_REGEXP);

    @InjectLog
    private Logger logger;

    private PreProcessRawObsService preProcessRawObsService;

    @Autowired
    public ReadRinexObservationsV211Impl(PreProcessRawObsService service) {
        preProcessRawObsService = service;
    }

    public void read(BufferedReader reader, ReceiverDataModel dataModel) throws Exception {
        if (reader == null || dataModel == null) {
            return;
        }
        while(reader.ready()) {
            String line = reader.readLine();
            Matcher matcher = EPOCH_TIME_PATTERN.matcher(line);
            logger.debug("Readed line: " + line);
            if (!matcher.find()) {
                logger.warn("Can't parse: " + line + " Line will be skipped.");
                continue;
            } else {
                try {
                    LocalDateTime epochTime = DateUtil.parseObsToLocalDateTime(matcher.group(1));
                    logger.debug(epochTime.toString());
                    int epochFlag = Integer.parseInt(matcher.group(2).trim());
                    int expectedSatNum = Integer.parseInt(matcher.group(3).trim());
                    List<String> satList = Splitter.fixedLength(3).splitToList(matcher.group(4).trim());
                    logger.debug("epochFlag: " + epochFlag + " Satellites: " + satList);
                    if (expectedSatNum != satList.size()) {
                        logger.warn("Excpected " + expectedSatNum + " satellites. But found " + satList.size() + " - Epoch will be skipped.");
                        continue;
                    }

                    EpochDto epoch = new EpochDto(epochTime);
                    epoch.setFlag(epochFlag);
                    epoch.setNumSv(expectedSatNum);
                    epoch.setSatellites(satList);

                    Map<String, String> rawEpochData = new LinkedHashMap<>();
                    Map<String, double[]> parsedEpochData = new LinkedHashMap<>();
                    for (String sat : satList) {
                        String rawObs = preProcessRawObsService.preProcess(reader);
                        logger.debug("Raw satellites data: Sat: " + sat + " Obs: " + rawObs);
                        double[] obs = preProcessRawObsService.convertRawObs(rawObs);
                        rawEpochData.put(sat, rawObs);
                        parsedEpochData.put(sat, obs);
                    }
                    epoch.setRawEpochData(rawEpochData);
                    epoch.setParsedEpochData(parsedEpochData);
                    dataModel.addObservations(epoch);
                } catch (Exception e) {
                    logger.error("Can't parse line: " + line + ". Epoch will be skipped.");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppInitializer.class);
        context.register(MvcConfiguration.class);
        context.register(BusinessHibernateConfig.class);
        context.registerBean(LogInjector.class);
        context.scan("ppa", "utils", "config");

        ReceiverDataModel dataModel = new ReceiverDataModel();
        TypesOfObsParserServiceImpl parser = new TypesOfObsParserServiceImpl();
        TypesOfObs typesOfObs = parser.parse("     4    C1    L1    L2    P2                              # / TYPES OF OBSERV");
        dataModel.setTypesOfObs(typesOfObs);

        ClassLoader classLoader = ReadRinexObservationsV211Impl.class.getClassLoader();
        String fullPath = classLoader.getResource("readObsByBlocks.16o").getFile().replaceFirst("/", "");
        BufferedReader reader = new BufferedReader(new FileReader(fullPath));

        ReadRinexObservationsV211Impl obsReader = context.getBean(ReadRinexObservationsV211Impl.class);
        obsReader.read(reader, dataModel);

//        RealMatrix matrix = dataModel.getObs().get(ObsType.C1);
//
//        Figure figure = new Figure();
//        figure.plot(matrix);
    }
}
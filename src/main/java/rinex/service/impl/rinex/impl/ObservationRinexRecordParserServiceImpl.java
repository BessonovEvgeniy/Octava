package rinex.service.impl.rinex.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;
import rinex.dto.EpochDto;
import rinex.model.observation.ReceiverDataModel;
import rinex.model.observation.header.impl.TypesOfObs;
import rinex.service.impl.observations.header.impl.TypesOfObsParserServiceImpl;
import rinex.service.impl.utils.date.DateUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ObservationRinexRecordParserServiceImpl {

    public final static String EPOCH_TIME_REGEXP = "(" + Strings.repeat("\\s*\\d{1,2}",5) + "\\s*\\d{1,2}.\\d{7})\\s{2}(\\d{1})\\s{2}(\\d{1})(.*)";
    public final static Pattern EPOCH_TIME_PATTERN = Pattern.compile(EPOCH_TIME_REGEXP);

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public void parse(BufferedReader reader, ReceiverDataModel dataModel) throws Exception {

        while(reader.ready()) {

            String line = reader.readLine();
            Matcher matcher = EPOCH_TIME_PATTERN.matcher(line);

            if (!matcher.find()) {
                System.out.println("Can't parse: " + line + ". Line will be skipped.");
                continue;
            } else {

                LocalDateTime epochTime = DateUtil.parseObsToLocalDateTime(matcher.group(1));
                int expectedSatNum = Ints.tryParse(matcher.group(3).trim());
                List<String> sats = Splitter.fixedLength(3).splitToList(matcher.group(4).trim());

                if (expectedSatNum != sats.size()) {
                    System.out.println("Excpected " + expectedSatNum + " satellites. But found " + sats.size() + ". Epoch will be skipped.");
                    continue;
                }

                EpochDto epoch = new EpochDto(epochTime);
                epoch.setNumSv(expectedSatNum);
                epoch.setSatellites(sats);

                Map<String, String> epochData = epoch.getRawEpochData();
                for (String sat : sats) {
                    if (reader.ready()) {
                        epochData.put(sat, reader.readLine());
                    } else {
                        return;
                    }
                }
                dataModel.addObservations(epoch);
            }
        }
    }

    public static void main(String[] args) throws Exception {

        ReceiverDataModel dataModel = new ReceiverDataModel();
        TypesOfObsParserServiceImpl parser = new TypesOfObsParserServiceImpl();
        TypesOfObs typesOfObs = parser.parse("     4    C1    L1    L2    P2                              # / TYPES OF OBSERV");
        dataModel.setTypesOfObs(typesOfObs);

        ClassLoader classLoader = ObservationRinexRecordParserServiceImpl.class.getClassLoader();
        String fullPath = classLoader.getResource("readObsByBlocks.16o").getFile().replaceFirst("/", "");
        BufferedReader reader = new BufferedReader(new FileReader(fullPath));

        ObservationRinexRecordParserServiceImpl obsReader = new ObservationRinexRecordParserServiceImpl();
        obsReader.parse(reader, dataModel);

    }
}

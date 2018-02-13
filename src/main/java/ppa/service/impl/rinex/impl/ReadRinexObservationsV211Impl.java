package ppa.service.impl.rinex.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;
import ppa.dto.EpochDto;
import ppa.model.observation.ReceiverDataModel;
import ppa.model.observation.header.impl.TypesOfObs;
import ppa.service.State;
import ppa.service.impl.observations.header.impl.TypesOfObsParserServiceImpl;
import utils.date.DateUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("2.11")
class ReadRinexObservationsV211Impl extends AbstractReadRinexObservations implements State {

    public final static String EPOCH_TIME_REGEXP = "(" + Strings.repeat("\\s*\\d{1,2}",5) + "\\s*\\d{1,2}.\\d{7})\\s{2}(\\d{1})\\s{2}(\\d{1})(.*)";
    public final static Pattern EPOCH_TIME_PATTERN = Pattern.compile(EPOCH_TIME_REGEXP);
    public final static List<String> LABELS_TO_SKIP = Arrays.asList("COMMENT", "MARKER NAME", "MARKER NUMBER", "ANTENNA: DELTA H/E/N");

    public void read(BufferedReader reader, ReceiverDataModel dataModel) throws Exception {
        while(reader.ready()) {
            String line = reader.readLine();
            Matcher matcher = EPOCH_TIME_PATTERN.matcher(line);

            if (!matcher.find()) {
                System.out.println("Can't parse: " + line + " Line will be skipped.");
                continue;
            } else {
                LocalDateTime epochTime = DateUtil.parseObsToLocalDateTime(matcher.group(1));
                int epochFlag = Ints.tryParse(matcher.group(2).trim());
                int expectedSatNum = Ints.tryParse(matcher.group(3).trim());
                List<String> satList = Splitter.fixedLength(3).splitToList(matcher.group(4).trim());

                if (expectedSatNum != satList.size()) {
                    System.out.println("Excpected " + expectedSatNum + " satellites. But found " + satList.size() + " - Epoch will be skipped.");
                    continue;
                }

                EpochDto epoch = new EpochDto(epochTime);
                epoch.setFlag(epochFlag);
                epoch.setNumSv(expectedSatNum);
                epoch.setSatellites(satList);

                Map<String, String> epochData = new LinkedHashMap<>();
                for (String sat : satList) {
                    if (reader.ready()) {
                        line = skipCommentsIfAny(reader);
                        if (line.isEmpty()) {
                            return;
                        }
                        epochData.put(sat, line);
                    } else {
                        return;
                    }
                }
                epoch.add(epochData);
                dataModel.addObservations(epoch);
            }
        }
    }

    private String skipCommentsIfAny(BufferedReader reader) throws IOException {
        boolean skip = false;
        String line;
        do {
            line = reader.readLine();
            for (String label : LABELS_TO_SKIP) {
                if (skip = line.contains(label)) {
                    break;
                }
            }
        } while (skip);
        return line;
    }

    public static void main(String[] args) throws Exception {

        ReceiverDataModel dataModel = new ReceiverDataModel();
        TypesOfObsParserServiceImpl parser = new TypesOfObsParserServiceImpl();
        TypesOfObs typesOfObs = parser.parse("     4    C1    L1    L2    P2                              # / TYPES OF OBSERV");
        dataModel.setTypesOfObs(typesOfObs);

        ClassLoader classLoader = ReadRinexObservationsV211Impl.class.getClassLoader();
        String fullPath = classLoader.getResource("readObsByBlocks.16o").getFile().replaceFirst("/", "");
        BufferedReader reader = new BufferedReader(new FileReader(fullPath));

        ReadRinexObservationsV211Impl obsReader = new ReadRinexObservationsV211Impl();
        obsReader.read(reader, dataModel);

    }
}
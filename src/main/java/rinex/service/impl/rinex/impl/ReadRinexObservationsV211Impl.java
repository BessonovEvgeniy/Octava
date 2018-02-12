package rinex.service.impl.rinex.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import org.springframework.stereotype.Service;
import rinex.dto.EpochDto;
import rinex.model.observation.ReceiverDataModel;
import rinex.model.observation.header.impl.TypesOfObs;
import rinex.service.State;
import rinex.service.impl.observations.header.impl.TypesOfObsParserServiceImpl;
import rinex.service.impl.utils.date.DateUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
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
//            String line = skipCommentsIfAny(reader);
//            if (line.isEmpty()) {
//                return;
//            }

            Matcher matcher = EPOCH_TIME_PATTERN.matcher(line);

            if (!matcher.find()) {
                System.out.println("Can't parse: " + line + " Line will be skipped.");
                continue;
            } else {

                LocalDateTime epochTime = DateUtil.parseObsToLocalDateTime(matcher.group(1));
                int expectedSatNum = Ints.tryParse(matcher.group(3).trim());
                List<String> sats = Splitter.fixedLength(3).splitToList(matcher.group(4).trim());

                if (expectedSatNum != sats.size()) {
                    System.out.println("Excpected " + expectedSatNum + " satellites. But found " + sats.size() + " - Epoch will be skipped.");
                    continue;
                }

                EpochDto epoch = new EpochDto(epochTime);
                epoch.setNumSv(expectedSatNum);
                epoch.setSatellites(sats);

                Map<String, String> epochData = epoch.getRawEpochData();
                for (String sat : sats) {
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


//    @Override
//    protected EpochDto readEpoch(BufferedReader reader) throws Exception {
//        TypesOfObs types = model.getTypesOfObs();
//
//        EpochDto epochDto = new EpochDto();
//
//        Map<String, List<String>> obs = new LinkedHashMap<>();
//        try {
//            String timeData = line.substring(0,32);
//            String satData = line.substring(32, line.length());
//
//            List<String> time = splitDataBySpace(timeData);
//            List<String> sat = splitSatDataHeader(satData);
//
//            Integer obsFlag = Integer.parseInt(time.get(time.size() - 1));
//            Integer numSv = Integer.parseInt(time.get(time.size() - 1));
//
//            for (Integer sv = 0; sv < numSv; sv++) {
//                if ((line = reader.readLine()) != null) {
//                    obs.put(sat.get(sv), splitDataBySpace(line));
//                } else {
//                    throw new Exception();
//                }
//            }
////            epochDto.setTime(time);
////            epochDto.setFlag(obsFlag);
////            epochDto.setNumSv(numSv);
////            epochDto.setSvPattern(sat);
////            epochDto.setRawObs(obs);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        return epochDto;
//    }
//
//    @Override
//    public void read(BufferedReader reader, ReceiverDataModel data) throws Exception {
//        while ((line = reader.readLine()) != null) {
//            if (!line.contains("COMMENT")) {
//                EpochDto epochDto = readEpoch(reader);
//
//                Map<ObsType, Observations> allObs = data.getObs();
//
//                for (ObsType type : data.getTypesOfObs().getObsTypes()) {
//                    Observations obs;
//                    if (allObs.get(type) == null) {
//                        obs = new Observations(type);
//                        allObs.put(type, obs);
//                    } else {
//                        obs = allObs.get(type);
//                    }
//                    System.out.println(line);
////                    obs.add(epochDto.getObservations(type));
//                }
//            }
//        }
//    }
//}
package ppa.service.impl.rinex;

import org.springframework.stereotype.Service;
import ppa.service.PreProcessRawObsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PreProcessRawObsServiceImpl implements PreProcessRawObsService {

    public final static List<String> LABELS_TO_SKIP = Arrays.asList("COMMENT", "MARKER NAME", "MARKER NUMBER", "ANTENNA: DELTA H/E/N");
    public final static Pattern PRE_PROCESS_PATTERN = Pattern.compile("^((-?\\d{1,12}\\.\\d{3,5})\\s?\\d?).*");

    private Predicate<String> predicate = line ->
            LABELS_TO_SKIP.parallelStream().filter(label -> line.contains(label)).findAny().isPresent();

    public String preProcess(BufferedReader reader) throws IOException {
        return skipCommentsIfAny(reader);
    }

    public double[] convertRawObs(String rawObs) {
        //Replace observations gaps by Zero values
        rawObs = rawObs.trim().replaceAll("\\s{14,28}", "          0.000 ");

        List<Double> obs = new LinkedList<>();
        Matcher matcher = PRE_PROCESS_PATTERN.matcher(rawObs);
        while (matcher.find()) {
            String obsValue = matcher.group(2);
            obs.add(Double.parseDouble(obsValue));
            rawObs = rawObs.replaceFirst(matcher.group(1), "").trim();
            matcher = PRE_PROCESS_PATTERN.matcher(rawObs);
        }
        return obs.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public String skipCommentsIfAny(BufferedReader reader) throws IOException {
        String line;
        do {
            line = reader.readLine();
        } while (predicate.test(line));
        return line;
    }

    public static void main(String[] args) {
        String rawObsBeforeProcess = "  24012083.305 4  -1199122.297 4   -901414.88346  24012086.17646";
        String etalonProcess = "  24012083.30504  -1199122.29704   -901414.88346  24012086.17646";

        String rawObsBeforeSplit = " 22313122.227 7 -10127204.711 7  -7858357.09448  22313123.61348";
        rawObsBeforeSplit = " 22313122.227 7 -10127204.711 7                  22313123.61348";

        PreProcessRawObsServiceImpl preProcessor = new PreProcessRawObsServiceImpl();
        preProcessor.convertRawObs(rawObsBeforeSplit);
    }
}

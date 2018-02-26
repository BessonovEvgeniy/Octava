package ppa.service.impl.rinex.impl;

import com.google.common.primitives.Doubles;
import config.injector.InjectLog;
import jdk.nashorn.internal.runtime.ParserException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ppa.service.PreProcessRawObsService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PreProcessRawObsServiceImpl implements PreProcessRawObsService {

    public final static List<String> LABELS_TO_SKIP = Arrays.asList("COMMENT", "MARKER NAME", "MARKER NUMBER", "ANTENNA: DELTA H/E/N");

    @InjectLog
    private Logger logger;

    public String preProcess(BufferedReader reader) throws IOException {
        String line = "";
        if (reader != null && reader.ready()) {
            line = skipCommentsIfAny(reader);
            if (!line.isEmpty()) {
                line = preProcess(line);
            }
        }
        return line;
    }

    public String preProcess(String line) {

        Matcher matcher = Pattern.compile(".*(\\.\\d{1,3}\\s\\d{1,2}).*").matcher(line);

        while (matcher.find()) {
            String fraction = matcher.group(1);
            String fractionWoZeros = fraction.replaceAll(" ", "0");
            line = line.replaceAll(fraction, fractionWoZeros);
            matcher = Pattern.compile(".*(\\.\\d{1,3}\\s\\d{1,2}).*").matcher(line);
        }

        return line;
    }

    public double[] convertAndPreProcessRawObs(String rawObs, int numTypesOfObs) throws ParserException {
        String[] splitedRawObs = rawObs.trim().split("    |   |  | ");
        double[] epochData = new double[numTypesOfObs];
        Arrays.fill(epochData, 0);

        try {
            int counter = 0;
            for (String str : splitedRawObs) {
                if (!str.trim().isEmpty()) {
                    epochData[counter] = Doubles.tryParse(str);
                    counter++;
                }
            }
        } catch (Exception e) {
            String msg = "Raw data: " + Arrays.asList(splitedRawObs) + " Parsed Data: " + Arrays.asList(epochData);
            logger.error(msg);
            throw new ParserException(msg);
        }
        return epochData;
    }

    public String skipCommentsIfAny(BufferedReader reader) throws IOException {
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

    public static void main(String[] args) {
        String rawObsBeforeProcess = "  24012083.305 4  -1199122.297 4   -901414.88346  24012086.17646";
        String etalonProcess = "  24012083.30504  -1199122.29704   -901414.88346  24012086.17646";

        PreProcessRawObsServiceImpl preProcessor = new PreProcessRawObsServiceImpl();
        String rawObsAfterProcess = preProcessor.preProcess(rawObsBeforeProcess);
    }
}

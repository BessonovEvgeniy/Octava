package ppa.service.impl.rinex.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import config.AppInitializer;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import ppa.dto.EpochDto;
import ppa.model.observation.ReceiverDataModel;
import ppa.model.observation.header.impl.TypesOfObs;
import ppa.service.PreProcessRawObsService;
import ppa.service.State;
import ppa.service.impl.observations.header.impl.TypesOfObsParserServiceImpl;
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
public class ReadRinexObservationsV211Impl extends AbstractReadRinexObservations implements State {

    public final static String EPOCH_TIME_REGEXP = "(" + Strings.repeat("\\s*\\d{1,2}",5) + "\\s*\\d{1,2}.\\d{7})\\s{2}(\\d{1})\\s{2}(\\d{1})(.*)";
    public final static Pattern EPOCH_TIME_PATTERN = Pattern.compile(EPOCH_TIME_REGEXP);

    @Autowired
    private PreProcessRawObsService preProcessRawObsService;

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
                    String rawObs = preProcessRawObsService.preProcess(reader);
                    epochData.put(sat, rawObs);
                }
                epoch.add(epochData);
                dataModel.addObservations(epoch);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppInitializer.class);
        context.scan("ppa");

        ReceiverDataModel dataModel = new ReceiverDataModel();
        TypesOfObsParserServiceImpl parser = new TypesOfObsParserServiceImpl();
        TypesOfObs typesOfObs = parser.parse("     4    C1    L1    L2    P2                              # / TYPES OF OBSERV");
        dataModel.setTypesOfObs(typesOfObs);

        ClassLoader classLoader = ReadRinexObservationsV211Impl.class.getClassLoader();
        String fullPath = classLoader.getResource("readObsByBlocks.16o").getFile().replaceFirst("/", "");
        BufferedReader reader = new BufferedReader(new FileReader(fullPath));

        ReadRinexObservationsV211Impl obsReader = context.getBean(ReadRinexObservationsV211Impl.class);
        obsReader.read(reader, dataModel);

        XYChart chart = new XYChartBuilder().width(800).height(600).title("Test from main").xAxisTitle("X").yAxisTitle("Y").build();
        chart.addSeries("C1", new double[] { -3, 5, 9, 6, 5 }, new double[] { -3, 5, 9, 6, 5 });
        new SwingWrapper<XYChart>(chart).displayChart();
    }
}
package ppa.service.impl.observations.header;

import com.google.common.base.Strings;
import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.TimeOfFirstObs;
import ppa.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("TIME OF FIRST OBS")
public class TimeOfFirstObsParserServiceImpl implements HeaderLabelParserService<TimeOfFirstObs> {

    public final static Pattern PATTERN = Pattern.compile(".*" +
            "(\\d{4}.{4,5}"  +
            Strings.repeat("\\d{1,2}.{4,5}", 4) +
            "\\d{1,2}\\.\\d{7}).{3,5}"+
            "(\\w{3}).*TIME OF FIRST OBS   ");

    @Override
    public TimeOfFirstObs parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

//        if (matcher.find()) {
//            system = isFind ? matcher.group(2) : "";
//            try {
//                String clearedDate = matcher.group(1).trim().replaceAll("\\s{2,7}"," ");
//                timeOfObs.parse(clearedDate);
//            } catch (ParseException e) {
//                e.printStackTrace();
//                throw new UnknownHeaderLabelException("Can't parse line to simple format date.");
//            }
//        }
        return null;
    }
}

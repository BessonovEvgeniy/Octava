package rinex.model.observations.header;

import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public @Data class AbstractTimeOfObs implements HeaderLabel {

    private String system;

    private SimpleDateFormat timeOfObs = new SimpleDateFormat("yyyy MM dd HH mm ss.SSSSSSS");

    private Pattern pattern = Pattern.compile(".*" +
            "(\\d{4}.{4,5}"  +
            Strings.repeat("\\d{1,2}.{4,5}", 4) +
            "\\d{1,2}\\.\\d{7}).{3,5}"+
            "(\\w{3}).*TIME OF FIRST OBS");

    @Override
    public boolean parse(String line) {

        Matcher matcher = pattern.matcher(line);

        Boolean isFind = matcher.find();
        system = isFind ? matcher.group(2) : "";
        if (isFind) {
            try {
                String clearedDate = matcher.group(1).trim().replaceAll("\\s{2,7}"," ");
                timeOfObs.parse(clearedDate);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RinexHeaderException("Can't parse line to simple format date.");
            }
        }
        return isFind;
    }
}

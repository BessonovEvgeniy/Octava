package rinex.service.Impl.observations.rinex.rinexImpl;

import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObservationRinexRecord implements HeaderLabel {

    @Override
    public boolean parse(String line) {
        line = " 05  3 24 13 10";
        Pattern epochTime = Pattern.compile("^( \\d{1,2}){5}");
        Matcher matcher = epochTime.matcher(line);

        if (matcher.find()) {
            String data = matcher.group();
        }

        return false;
    }
}

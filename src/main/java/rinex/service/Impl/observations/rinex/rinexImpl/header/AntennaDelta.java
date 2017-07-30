package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Component;
import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("antennaDelta")
public @Data class AntennaDelta implements HeaderLabel {

    private Double h;
    private Double e;
    private Double n;

    @Override
    public boolean parse(String line) {

        Matcher matcher = Pattern.compile("(\\d{1,14}\\.\\d{1,4})(\\d{1,14}\\.\\d{1,4})(\\d{1,14}\\.\\d{1,4})" +
                "(APPROX POSITION XYZ)").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            h = Double.parseDouble(matcher.group(1).trim());
            e = Double.parseDouble(matcher.group(2).trim());
            n = Double.parseDouble(matcher.group(3).trim());
        } else {
            h = 0.0;
            e = 0.0;
            n = 0.0;
        }
        return isFind;
    }
}

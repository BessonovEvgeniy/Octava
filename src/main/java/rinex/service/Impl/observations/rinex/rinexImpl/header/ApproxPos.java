package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Component;
import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("approxPos")
public @Data class ApproxPos implements HeaderLabel {

    private Double x;
    private Double y;
    private Double z;

    @Override
    public boolean parse(String line) {

        Matcher matcher = Pattern.compile("([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,6}" +
                                          "([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,6}" +
                                          "([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,18}" +
                                          "(APPROX POSITION XYZ)").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            x = Double.parseDouble(matcher.group(1).trim());
            y = Double.parseDouble(matcher.group(2).trim());
            z = Double.parseDouble(matcher.group(3).trim());
        } else {
            x = 0.0;
            y = 0.0;
            z = 0.0;
        }
        return isFind;
    }
}

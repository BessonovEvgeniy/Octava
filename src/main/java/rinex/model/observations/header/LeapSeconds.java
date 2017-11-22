package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("LEAP SECONDS")
public @Data class LeapSeconds implements HeaderLabel {

    private int leapSeconds;

    private Pattern pattern = Pattern.compile("(.{1,60})LEAP SECONDS");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseOneParam(pattern, line);
        if (find) {
            List<Integer> params = parser.getIntegerParams();
            leapSeconds = params.get(0);
        }
        return find;
    }
}

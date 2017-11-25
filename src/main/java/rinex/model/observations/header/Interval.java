package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("INTERVAL")
public @Data class Interval implements HeaderLabel {

    private double interval;

    private Pattern pattern = Pattern.compile("(\\d{1,7}\\.\\d{1,4}).*INTERVAL");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseOneParam(pattern, line);
        if (find) {
            List<Double> params = parser.getDoubleParams();
            interval = params.get(0);
        }
        return find;
    }
}

package rinex.model.observations.header;

import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Pattern;

@Component("APPROX POSITION XYZ")
public @Data class ApproxPos implements HeaderLabel {

    private double x;
    private double y;
    private double z;

    private Pattern pattern = Pattern.compile(Strings.repeat("([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,6}",2) +
                    "([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,18}" +
                    "APPROX POSITION XYZ");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseThreeParams(pattern, line);
        if (find) {
            List<Double> params = parser.getDoubleParams();
            x = params.get(0);
            y = params.get(1);
            z = params.get(2);
        }
        return find;
    }
}

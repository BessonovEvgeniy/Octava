package rinex.model.observations.header;

import com.google.common.base.Strings;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("ANTENNA: DELTA H/E/N")
public @Getter class AntennaDelta implements HeaderLabel {

    private double delH;
    private double delE;
    private double delN;

    private Pattern pattern = Pattern.compile(Strings.repeat("(\\d{1,14}\\.\\d{1,4})",3) + "(APPROX POSITION XYZ)");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseThreeParams(pattern, line);
        if (find) {
            List<Double> params = parser.getDoubleParams();
            delH = params.get(0);
            delE = params.get(1);
            delN = params.get(2);
        }
        return find;
    }
}
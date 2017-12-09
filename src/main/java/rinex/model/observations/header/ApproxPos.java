package rinex.model.observations.header;

import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("APPROX POSITION XYZ")
public @Data class ApproxPos implements HeaderLabel {

    private double x;
    private double y;
    private double z;

    private Pattern pattern = Pattern.compile("\\s{2}" + Strings.repeat("([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,23}",3) + "APPROX POSITION XYZ ");

    @Override
    public boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);

        boolean find = matcher.find();
        if (find) {
            x = Doubles.tryParse(matcher.group(1));
            y = Doubles.tryParse(matcher.group(2));
            z = Doubles.tryParse(matcher.group(3));
        }
        return find;
    }
}

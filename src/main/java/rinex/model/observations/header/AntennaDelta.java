package rinex.model.observations.header;

import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("ANTENNA: DELTA H/E/N")
public @Getter class AntennaDelta implements HeaderLabel {

    private double delH;
    private double delE;
    private double delN;

    private Pattern pattern = Pattern.compile("\\s{1,2}"
            + Strings.repeat("([-+]?[0-9]{0,14}\\.?[0-9]{0,4})\\s{1,23}",3)
            + "ANTENNA: DELTA H/E/N");

    @Override
    public boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);

        boolean find = matcher.find();
        if (find) {
            delH = Doubles.tryParse(matcher.group(1));
            delE = Doubles.tryParse(matcher.group(2));
            delN = Doubles.tryParse(matcher.group(3));
        }
        return find;
    }
}
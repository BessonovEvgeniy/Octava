package ppa.service.impl.observations.header;

import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.AntennaDelta;
import ppa.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ANTENNA: DELTA H/E/N")
public class AntennaDeltaParserServiceImpl implements HeaderLabelParserService<AntennaDelta> {

    public final static Pattern PATTERN = Pattern.compile("\\s{1,2}"
            + Strings.repeat("([-+]?[0-9]{0,14}\\.?[0-9]{0,4})\\s{1,23}",3)
            + "ANTENNA: DELTA H/E/N");

    @Override
    public AntennaDelta parse(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            double delH = Doubles.tryParse(matcher.group(1));
            double delE = Doubles.tryParse(matcher.group(2));
            double delN = Doubles.tryParse(matcher.group(3));
            return new AntennaDelta(delH, delE, delN);
        }
        return AntennaDelta.NULL;
    }
}
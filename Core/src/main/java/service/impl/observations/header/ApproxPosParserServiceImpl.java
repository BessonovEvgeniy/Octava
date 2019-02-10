package service.impl.observations.header;

import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import model.observation.header.impl.ApproxPos;
import org.springframework.stereotype.Service;
import service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("APPROX POSITION XYZ")
public class ApproxPosParserServiceImpl implements HeaderLabelParserService<ApproxPos> {

    public final static Pattern PATTERN = Pattern.compile("\\s{2}" +
            Strings.repeat("([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,23}",3) +
            "APPROX POSITION XYZ ");

    @Override
    public ApproxPos parse(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            double x = Doubles.tryParse(matcher.group(1));
            double y = Doubles.tryParse(matcher.group(2));
            double z = Doubles.tryParse(matcher.group(3));

            return new ApproxPos(x, y, z);
        }
        return ApproxPos.NULL;
    }
}

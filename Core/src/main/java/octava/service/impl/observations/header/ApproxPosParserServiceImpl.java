package octava.service.impl.observations.header;

import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import octava.model.observation.header.impl.ApproxPosModel;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("APPROX POSITION XYZ")
public class ApproxPosParserServiceImpl implements HeaderLabelParserService<ApproxPosModel> {

    public final static Pattern PATTERN = Pattern.compile("\\s{2}" +
            Strings.repeat("([-+]?[0-9]{1,14}\\.?[0-9]{0,4})\\s{1,23}",3) +
            "APPROX POSITION XYZ ");

    @Override
    public ApproxPosModel parse(final String line) {
        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final double x = Doubles.tryParse(matcher.group(1));
            final double y = Doubles.tryParse(matcher.group(2));
            final double z = Doubles.tryParse(matcher.group(3));

            return new ApproxPosModel(x, y, z);
        }
        return ApproxPosModel.NULL;
    }
}

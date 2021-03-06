package octava.service.impl.observations.header;

import com.google.common.base.Strings;
import com.google.common.primitives.Doubles;
import octava.model.observation.header.impl.AntennaDeltaModel;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ANTENNA: DELTA H/E/N")
public class AntennaDeltaParserServiceImpl implements HeaderLabelParserService<AntennaDeltaModel> {

    public final static Pattern PATTERN = Pattern.compile("\\s{1,2}"
            + Strings.repeat("([-+]?[0-9]{0,14}\\.?[0-9]{0,4})\\s{1,23}",3)
            + "ANTENNA: DELTA H/E/N");

    @Override
    public AntennaDeltaModel parse(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            double delH = Doubles.tryParse(matcher.group(1));
            double delE = Doubles.tryParse(matcher.group(2));
            double delN = Doubles.tryParse(matcher.group(3));
            return new AntennaDeltaModel(delH, delE, delN);
        }
        return AntennaDeltaModel.NULL;
    }
}
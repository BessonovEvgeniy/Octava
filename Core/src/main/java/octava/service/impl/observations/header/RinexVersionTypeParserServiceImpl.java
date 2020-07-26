package octava.service.impl.observations.header;

import octava.model.observation.header.impl.RinexVersionTypeModel;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("RINEX VERSION / TYPE")
public class RinexVersionTypeParserServiceImpl implements HeaderLabelParserService<RinexVersionTypeModel> {

    public final static Pattern PATTERN = Pattern.compile("\\s{5}(\\d{1,9}\\.\\d{1,2})\\s{4,11}[\\w,\\s]{20}([M,G,E,R,S]).{19}RINEX VERSION / TYPE");

    @Override
    public RinexVersionTypeModel parse(final String line) {

        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final String version = matcher.group(1).trim();
            final String mode = matcher.group(2).trim();

            return new RinexVersionTypeModel(version, mode);
        }
        return RinexVersionTypeModel.NULL;
    }
}
package rinex.service.impl.observations.header.impl;

import org.springframework.stereotype.Service;
import rinex.model.observation.header.impl.RinexVersionType;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("RINEX VERSION / TYPE")
public class RinexVersionTypeParserServiceImpl implements HeaderLabelParserService<RinexVersionType> {

    private Pattern pattern = Pattern.compile("\\s{5}(\\d{1,9}\\.\\d{1,2})\\s{4,11}[\\w,\\s]{20}([M,G,E,R,S]).{19}RINEX VERSION / TYPE");

    @Override
    public RinexVersionType parse(String line) {

        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String version = matcher.group(1).trim();
            String mode = matcher.group(2).trim();

            return new RinexVersionType(version, mode);
        }
        return RinexVersionType.NULL;
    }
}
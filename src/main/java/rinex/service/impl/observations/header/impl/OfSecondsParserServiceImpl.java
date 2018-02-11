package rinex.service.impl.observations.header.impl;

import com.google.common.primitives.Ints;
import lombok.Data;
import org.springframework.stereotype.Service;
import rinex.model.observation.header.impl.OfSatellites;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("# OF SATELLITES")
public @Data class OfSecondsParserServiceImpl implements HeaderLabelParserService<OfSatellites> {

    private Pattern pattern = Pattern.compile("(.{1,60})# OF SATELLITES");

    @Override
    public OfSatellites parse(String line) {

        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            int ofSats = Ints.tryParse(matcher.group(1).trim());
            return new OfSatellites(ofSats);
        }
        return OfSatellites.NULL;
    }
}

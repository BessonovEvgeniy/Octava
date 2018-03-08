package ppa.service.impl.observations.header.impl;

import com.google.common.primitives.Ints;
import lombok.Data;
import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.OfSatellites;
import ppa.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("# OF SATELLITES")
public @Data class OfSecondsParserServiceImpl implements HeaderLabelParserService<OfSatellites> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})# OF SATELLITES");

    @Override
    public OfSatellites parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            int ofSats = Ints.tryParse(matcher.group(1).trim());
            return new OfSatellites(ofSats);
        }
        return OfSatellites.NULL;
    }
}

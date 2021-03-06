package octava.service.impl.observations.header;

import com.google.common.primitives.Ints;
import lombok.Data;
import octava.model.observation.header.impl.OfSatellites;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("# OF SATELLITES")
public @Data class OfSecondsParserServiceImpl implements HeaderLabelParserService<OfSatellites> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})# OF SATELLITES");

    @Override
    public OfSatellites parse(final String line) {

        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final int ofSats = Ints.tryParse(matcher.group(1).trim());
            return new OfSatellites(ofSats);
        }
        return OfSatellites.NULL;
    }
}

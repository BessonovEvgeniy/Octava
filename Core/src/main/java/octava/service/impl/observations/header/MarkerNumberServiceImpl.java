package octava.service.impl.observations.header;

import lombok.Data;
import octava.model.observation.header.impl.MarkerNumber;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("MARKER NUMBER")
public @Data class MarkerNumberServiceImpl implements HeaderLabelParserService<MarkerNumber> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})MARKER NUMBER");

    @Override
    public MarkerNumber parse(final String line) {

        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final String markerNumber = matcher.group(1).trim();
            return new MarkerNumber(markerNumber);
        }
        return MarkerNumber.NULL;
    }
}

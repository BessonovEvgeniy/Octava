package rinex.service.impl.observations.header.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import rinex.model.observations.header.impl.MarkerNumber;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("MARKER NUMBER")
public @Data class MarkerNumberServiceImpl implements HeaderLabelParserService<MarkerNumber> {

    private Pattern pattern = Pattern.compile("(.{1,60})MARKER NUMBER");

    @Override
    public MarkerNumber parse(String line) {

        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String markerNumber = matcher.group(1).trim();
            return new MarkerNumber(markerNumber);
        }
        return MarkerNumber.NULL;
    }
}

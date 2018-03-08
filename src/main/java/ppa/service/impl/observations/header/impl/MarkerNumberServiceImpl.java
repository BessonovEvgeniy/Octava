package ppa.service.impl.observations.header.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.MarkerNumber;
import ppa.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("MARKER NUMBER")
public @Data class MarkerNumberServiceImpl implements HeaderLabelParserService<MarkerNumber> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})MARKER NUMBER");

    @Override
    public MarkerNumber parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            String markerNumber = matcher.group(1).trim();
            return new MarkerNumber(markerNumber);
        }
        return MarkerNumber.NULL;
    }
}

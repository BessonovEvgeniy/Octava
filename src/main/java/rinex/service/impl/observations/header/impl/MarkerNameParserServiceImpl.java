package rinex.service.impl.observations.header.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import rinex.model.observation.header.impl.MarkerName;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("MARKER NAME")
public @Data class MarkerNameParserServiceImpl implements HeaderLabelParserService<MarkerName> {

    private Pattern pattern = Pattern.compile("(.{1,60})MARKER NAME");

    @Override
    public MarkerName parse(String line) {
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return new MarkerName(matcher.group(1));
        }
        return MarkerName.NULL;
    }
}

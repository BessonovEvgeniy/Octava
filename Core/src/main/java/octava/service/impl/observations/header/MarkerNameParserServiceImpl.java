package octava.service.impl.observations.header;

import lombok.Data;
import octava.model.observation.header.impl.MarkerName;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("MARKER NAME")
public @Data class MarkerNameParserServiceImpl implements HeaderLabelParserService<MarkerName> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})MARKER NAME");

    @Override
    public MarkerName parse(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            return new MarkerName(matcher.group(1));
        }
        return MarkerName.NULL;
    }
}

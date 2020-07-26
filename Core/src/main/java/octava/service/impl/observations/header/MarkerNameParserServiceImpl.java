package octava.service.impl.observations.header;

import lombok.Data;
import octava.model.observation.header.impl.MarkerNameModel;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("MARKER NAME")
public @Data class MarkerNameParserServiceImpl implements HeaderLabelParserService<MarkerNameModel> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})MARKER NAME");

    @Override
    public MarkerNameModel parse(final String line) {
        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            return new MarkerNameModel(matcher.group(1));
        }
        return MarkerNameModel.NULL;
    }
}

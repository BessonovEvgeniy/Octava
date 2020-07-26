package octava.service.impl.observations.header;

import com.google.common.primitives.Ints;
import lombok.Data;
import octava.model.observation.header.impl.LeapSecondsModel;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("LEAP SECONDS")
public @Data class LeapSecondsParserServiceImpl implements HeaderLabelParserService<LeapSecondsModel> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})LEAP SECONDS");

    @Override
    public LeapSecondsModel parse(final String line) {

        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final int leapSeconds = Ints.tryParse(matcher.group(1).trim());
            return new LeapSecondsModel(leapSeconds);
        }
        return LeapSecondsModel.NULL;
    }
}

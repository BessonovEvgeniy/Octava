package rinex.service.impl.observations.header.impl;

import com.google.common.primitives.Ints;
import lombok.Data;
import org.springframework.stereotype.Service;
import rinex.model.observations.header.impl.LeapSeconds;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("LEAP SECONDS")
public @Data class LeapSecondsParserServiceImpl implements HeaderLabelParserService<LeapSeconds> {

    private Pattern pattern = Pattern.compile("(.{1,60})LEAP SECONDS");

    @Override
    public LeapSeconds parse(String line) {

        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            int leapSeconds = Ints.tryParse(matcher.group(1));
            return new LeapSeconds(leapSeconds);
        }
        return LeapSeconds.NULL;
    }
}

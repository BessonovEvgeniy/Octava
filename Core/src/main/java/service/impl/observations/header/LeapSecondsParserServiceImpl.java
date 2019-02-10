package service.impl.observations.header;

import com.google.common.primitives.Ints;
import lombok.Data;
import model.observation.header.impl.LeapSeconds;
import org.springframework.stereotype.Service;
import service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("LEAP SECONDS")
public @Data class LeapSecondsParserServiceImpl implements HeaderLabelParserService<LeapSeconds> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})LEAP SECONDS");

    @Override
    public LeapSeconds parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            int leapSeconds = Ints.tryParse(matcher.group(1).trim());
            return new LeapSeconds(leapSeconds);
        }
        return LeapSeconds.NULL;
    }
}

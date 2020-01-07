package octava.service.impl.observations.header;

import com.google.common.primitives.Doubles;
import octava.model.observation.header.impl.Interval;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("INTERVAL")
public class IntervalParserServiceImpl implements HeaderLabelParserService<Interval> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})INTERVAL");

    @Override
    public Interval parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            double leapSeconds = Doubles.tryParse(matcher.group(1).trim());
            return new Interval(leapSeconds);
        }
        return Interval.NULL;
    }
}
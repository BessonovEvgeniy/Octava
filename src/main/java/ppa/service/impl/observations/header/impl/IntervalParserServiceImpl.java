package ppa.service.impl.observations.header.impl;

import com.google.common.primitives.Doubles;
import lombok.Data;
import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.Interval;
import ppa.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("INTERVAL")
public @Data class IntervalParserServiceImpl implements HeaderLabelParserService<Interval> {

    private Pattern pattern = Pattern.compile("(.{1,60})INTERVAL");

    @Override
    public Interval parse(String line) {

        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            double leapSeconds = Doubles.tryParse(matcher.group(1).trim());
            return new Interval(leapSeconds);
        }
        return Interval.NULL;
    }
}
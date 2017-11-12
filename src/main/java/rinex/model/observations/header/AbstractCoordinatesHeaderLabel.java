package rinex.model.observations.header;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component
public abstract class AbstractCoordinatesHeaderLabel extends AbstractHeaderLabel {

    protected Double x;
    protected Double y;
    protected Double z;

    @Override
    public Boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);
        isFind = matcher.find();
        x = isFind ? Double.parseDouble(matcher.group(1).trim()) : 0.0;
        y = isFind ? Double.parseDouble(matcher.group(2).trim()) : 0.0;
        z = isFind ? Double.parseDouble(matcher.group(3).trim()) : 0.0;
        return isFind;
    }
}

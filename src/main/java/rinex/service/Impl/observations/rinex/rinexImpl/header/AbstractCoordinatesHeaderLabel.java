package rinex.service.Impl.observations.rinex.rinexImpl.header;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;

@Service
public abstract class AbstractCoordinatesHeaderLabel extends AbstractHeaderLabel {

    protected Double x = 0.0;
    protected Double y = 0.0;
    protected Double z = 0.0;

    @Override
    public boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);
        isFind = matcher.find();
        if (isFind) {
            x = Double.parseDouble(matcher.group(1).trim());
            y = Double.parseDouble(matcher.group(2).trim());
            z = Double.parseDouble(matcher.group(3).trim());
        }
        return isFind;
    }
}

package rinex.model.observations.header;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public abstract class AbstractHeaderLabel implements HeaderLabel {

    protected Boolean isFind;
    protected String stringPattern;
    protected Pattern pattern;

    @Override
    public abstract Boolean parse(String line) throws RinexHeaderException;
}

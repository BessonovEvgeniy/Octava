package rinex.model.observations.header;

import java.util.regex.Pattern;

public abstract class AbstractHeaderLabel implements HeaderLabel {

    protected Boolean isFind;
    protected String stringPattern;
    protected Pattern pattern;

    @Override
    public abstract Boolean parse(String line) throws RinexHeaderException;
}

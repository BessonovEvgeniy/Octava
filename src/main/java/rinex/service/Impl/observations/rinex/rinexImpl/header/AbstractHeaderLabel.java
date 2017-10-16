package rinex.service.Impl.observations.rinex.rinexImpl.header;

import rinex.service.HeaderLabel;

import java.util.regex.Pattern;

public class AbstractHeaderLabel implements HeaderLabel {

    Boolean isFind;
    String stringPattern;
    Pattern pattern;

    @Override
    public Boolean parse(String line) throws RinexHeaderException {
        return false;
    }
}

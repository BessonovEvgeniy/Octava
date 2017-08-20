package rinex.service.Impl.observations.rinex.rinexImpl.header;

import rinex.service.HeaderLabel;

import java.util.regex.Pattern;

public class AbstractHeaderLabel implements HeaderLabel {

    protected boolean isFind = false;

    protected Pattern pattern;

    @Override
    public boolean parse(String line) throws RinexHeaderException {
        return isFind;
    }
}

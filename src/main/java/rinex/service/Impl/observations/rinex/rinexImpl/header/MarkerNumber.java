package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class MarkerNumber implements HeaderLabel {

    private String markerNumber;

    @Override
    public boolean parse(String line)  {
        Matcher matcher = Pattern.compile(".{1,60}").matcher(line);

        boolean isFind = matcher.find();
        markerNumber = isFind ? matcher.group().trim() : null;
        return isFind;
    }
}

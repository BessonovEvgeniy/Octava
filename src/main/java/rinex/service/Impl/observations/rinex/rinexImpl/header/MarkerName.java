package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Component;
import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class MarkerName implements HeaderLabel {

    private String markerName;

    @Override
    public boolean parse(String line)  {
        Matcher matcher = Pattern.compile(".{1,60}").matcher(line);

        boolean isFind = matcher.find();
        markerName = isFind ? matcher.group().trim() : null;
        return isFind;
    }
}

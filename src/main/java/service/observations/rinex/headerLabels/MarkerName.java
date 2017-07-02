package service.observations.rinex.headerLabels;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class MarkerName implements HeaderLabel {

    private String markerName;

    @Override
    public boolean parse(String line) {
        Matcher matcher = Pattern.compile(".{1,60}").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            markerName = matcher.group().trim();
        } else {
            markerName = null;
        }
        return isFind;
    }
}

package service.observations.rinex.headerLabels;

import lombok.Data;

import javax.persistence.Transient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class MarkerName implements HeaderLabel {

    private String markerName;

    @Override
    public boolean parse(String line) {
        Pattern markerNamePattern = Pattern.compile(".{1,60}");
        Matcher matcherNamePattern = markerNamePattern.matcher(line);

        if (matcherNamePattern.find()) {
            markerName = matcherNamePattern.group().trim();
        }
        return markerName.isEmpty();
    }
}

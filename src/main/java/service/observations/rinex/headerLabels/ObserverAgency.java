package service.observations.rinex.headerLabels;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class ObserverAgency implements HeaderLabel {

    private String observerName;
    private String agencyName;

    @Override
    public boolean parse(String line) {
        int agencyStartAt = 20;

        Pattern pattern = Pattern.compile(".{1,20}");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            observerName = matcher.group().trim();
        }

        pattern = Pattern.compile(".{1,40}");
        matcher = pattern.matcher(line);
        if (matcher.find(agencyStartAt)) {
            agencyName = matcher.group().trim();
        }
        return (observerName.isEmpty() && agencyName.isEmpty());
    }
}

package service.observations.rinex.headerLabels;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class ObserverAgency implements HeaderLabel {

    private String observerName;
    private String agencyName;

    @Override
    public boolean parse(String line) {

        Pattern pattern = Pattern.compile("(.{20})(.{40})(OBSERVER / AGENCY)");
        Matcher matcher = pattern.matcher(line);
        boolean isFind = matcher.find();
        if (isFind) {
            observerName = matcher.group(1).trim();
            agencyName = matcher.group(2).trim();
        }
        return isFind;
    }
}

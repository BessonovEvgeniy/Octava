package service.observations.rinex.rinexImpl.header;

import lombok.Data;
import service.observations.rinex.Proccess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class ObserverAgency implements Proccess {

    private String observerName;
    private String agencyName;

    @Override
    public boolean parse(String line) {
        Matcher matcher = Pattern.compile("(.{20})(.{40})(OBSERVER / AGENCY)").matcher(line);
        boolean isFind = matcher.find();
        if (isFind) {
            observerName = matcher.group(1).trim();
            agencyName = matcher.group(2).trim();
        } else {
            observerName = null;
            agencyName = null;
        }
        return isFind;
    }
}

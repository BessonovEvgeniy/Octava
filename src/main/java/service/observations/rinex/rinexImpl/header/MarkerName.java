package service.observations.rinex.rinexImpl.header;

import lombok.Data;
import service.observations.rinex.Proccess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class MarkerName implements Proccess {

    private String markerName;

    @Override
    public boolean parse(String line) {
        Matcher matcher = Pattern.compile(".{1,60}").matcher(line);

        boolean isFind = matcher.find();
        markerName = isFind ? matcher.group().trim() : null;
        return isFind;
    }
}

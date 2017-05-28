package service.observations.rinex.headerLabels;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class RinexVersionType implements HeaderLabel {

    private String version;
    private String mode;

    @Override
    public boolean parse(String line) {

        Pattern pattern = Pattern.compile("\\d\\.\\d{2}");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            version = matcher.group();
        }

        pattern = Pattern.compile("([G,R,S,T,M])");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            mode = matcher.group();
        }

        return (!version.isEmpty() && !version.isEmpty());
    }
}

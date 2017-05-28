package service.observations.rinex.headerLabels;

import lombok.Data;

import javax.persistence.Transient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class RinexVersionType implements HeaderLabel {

    private String version;

    private String mode;

    @Override
    public boolean parse(String line) {

        Pattern versionPattern = Pattern.compile("\\d\\.\\d{2}");
        Pattern modePattern = Pattern.compile("([G,R,S,T,M])");

        Matcher matcher;
        matcher = versionPattern.matcher(line);
        boolean isParsedVersion = matcher.find();

        if (isParsedVersion) {
            version = matcher.group();
        }
        matcher = modePattern.matcher(line);
        boolean isParsedMode = matcher.find();

        if (matcher.find()) {
            mode = matcher.group();
        }

        return (isParsedMode && isParsedVersion);
    }
}

package service.observations.rinex.headerLabels;

import lombok.Data;

import javax.persistence.Transient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class MarkerName implements HeaderLabel {

    private String version;

    private String mode;

    @Transient
    private Pattern pattern = Pattern.compile("\\d");

    @Override
    public boolean parse(String line) {

        Matcher m = pattern.matcher(line);
        if (m.find()) {

            return true;
        }
        return false;
    }
}

package service.observations.rinex.headerLabels;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class PgmRunByDate implements HeaderLabel {

    private String program;

    private String agency;

    @Temporal(TemporalType.DATE)
    private Date created;

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

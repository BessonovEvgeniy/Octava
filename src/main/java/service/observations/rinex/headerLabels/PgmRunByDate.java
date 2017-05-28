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
    private String created;

    @Override
    public boolean parse(String line) {

        Pattern pattern = Pattern.compile("^\\w{1,20}");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            program = matcher.group().trim();
        }

        pattern = Pattern.compile("\\s{1,20}\\w{1,20}\\s{1,20}");
        matcher= pattern.matcher(line);
        if (matcher.find()) {
            agency = matcher.group().trim();
        }

        pattern = Pattern.compile("\\d{1,20}\\s\\d{2}:\\d{2}:\\d{2}UTC");
        matcher = pattern.matcher(line);

        if (matcher.find(40)) {
            created = matcher.group();
        }

        return !(program.isEmpty() && agency.isEmpty() && created.isEmpty());
    }
}

package service.observations.rinex.headerLabels;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class PgmRunByDate implements HeaderLabel {

    private String program = "";
    private String agency = "";
    private String created = "";

    @Override
    public boolean parse(String line) {

        Pattern pattern = Pattern.compile(".{20}");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            program = matcher.group().trim();
        }

        pattern = Pattern.compile(".{20}");
        matcher= pattern.matcher(line);
        if (matcher.find(20)) {
            agency = matcher.group().trim();
        }

        pattern = Pattern.compile(".{20}");
        matcher = pattern.matcher(line);

        if (matcher.find(40)) {
            created = matcher.group().trim();
        }

        return !(program.isEmpty() && agency.isEmpty() && created.isEmpty());
    }
}

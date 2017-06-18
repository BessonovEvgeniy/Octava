package service.observations.rinex.headerLabels;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class PgmRunByDate implements HeaderLabel {

    private String program;
    private String agency;
    private String created;

    @Override
    public boolean parse(String line) {

        Pattern pattern = Pattern.compile("(.{20})(.{20})(.{20})(PGM / RUN BY / DATE)");
        Matcher matcher = pattern.matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            program = matcher.group(1).trim();
            agency = matcher.group(2).trim();
            created = matcher.group(3).trim();
        }
        return isFind;
    }
}

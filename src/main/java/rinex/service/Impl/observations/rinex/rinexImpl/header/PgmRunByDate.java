package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class PgmRunByDate implements HeaderLabel {

    private String program;
    private String agency;
    private String created;

    @Override
    public boolean parse(String line) {

        Matcher matcher = Pattern.compile("(.{20})(.{20})(.{20})(PGM / RUN BY / DATE)").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            program = matcher.group(1).trim();
            agency = matcher.group(2).trim();
            created = matcher.group(3).trim();
        } else {
            program = null;
            agency = null;
            created = null;
        }
        return isFind;
    }
}

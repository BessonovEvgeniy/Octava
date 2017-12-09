package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;
import rinex.service.impl.utils.date.DateUtil;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("PGM / RUN BY / DATE")
public @Data class PgmRunByDate implements HeaderLabel {

    private String program;
    private String agency;
    private LocalDateTime created;

    private Pattern pattern = Pattern.compile("(.{20})(.{20})(.{20})PGM / RUN BY / DATE");

    @Override
    public boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);

        boolean find = matcher.find();
        if (find) {
            program = matcher.group(1).trim();
            agency = matcher.group(2).trim();
            this.created = DateUtil.parseToLocalDateTime(matcher.group(3));
        }
        return find;
    }
}

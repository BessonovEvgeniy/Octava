package rinex.service.impl.observations.header.impl;

import org.springframework.stereotype.Service;
import rinex.model.observations.header.impl.PgmRunByDate;
import rinex.service.impl.observations.header.HeaderLabelParserService;
import rinex.service.impl.utils.date.DateUtil;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("PGM / RUN BY / DATE")
public class PgmRunByDateParserServiceImpl implements HeaderLabelParserService<PgmRunByDate> {

    private Pattern pattern = Pattern.compile("(.{20})(.{20})(.{20})PGM / RUN BY / DATE");

    @Override
    public PgmRunByDate parse(String line) {
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String program = matcher.group(1).trim();
            String agency = matcher.group(2).trim();
            LocalDateTime created = DateUtil.parseToLocalDateTime(matcher.group(3));

            return new PgmRunByDate(program, agency, created);
        }
        return PgmRunByDate.NULL;
    }
}

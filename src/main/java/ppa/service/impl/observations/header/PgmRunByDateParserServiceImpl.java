package ppa.service.impl.observations.header;

import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.PgmRunByDate;
import ppa.service.HeaderLabelParserService;
import utils.date.DateUtil;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("PGM / RUN BY / DATE")
public class PgmRunByDateParserServiceImpl implements HeaderLabelParserService<PgmRunByDate> {

    public final static Pattern PATTERN = Pattern.compile("(.{20})(.{20})(.{20})PGM / RUN BY / DATE");

    @Override
    public PgmRunByDate parse(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            String program = matcher.group(1).trim();
            String agency = matcher.group(2).trim();
            LocalDateTime created = DateUtil.parseToLocalDateTime(matcher.group(3));

            return new PgmRunByDate(program, agency, created);
        }
        return PgmRunByDate.NULL;
    }
}

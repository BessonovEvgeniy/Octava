package octava.service.impl.observations.header;

import octava.model.observation.header.impl.PgmRunByDateModel;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;
import octava.util.date.DateUtil;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("PGM / RUN BY / DATE")
public class PgmRunByDateParserServiceImpl implements HeaderLabelParserService<PgmRunByDateModel> {

    public final static Pattern PATTERN = Pattern.compile("(.{20})(.{20})(.{20})PGM / RUN BY / DATE");

    @Override
    public PgmRunByDateModel parse(final String line) {
        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final String program = matcher.group(1).trim();
            final String agency = matcher.group(2).trim();
            final LocalDateTime created = DateUtil.parseToLocalDateTime(matcher.group(3));

            return new PgmRunByDateModel(program, agency, created);
        }
        return PgmRunByDateModel.NULL;
    }
}

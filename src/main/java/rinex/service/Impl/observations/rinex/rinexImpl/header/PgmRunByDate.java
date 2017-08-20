package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class PgmRunByDate extends AbstractHeaderLabel {

    private String program;
    private String agency;
    private String created;

    public PgmRunByDate() {
        init();
    }

    @PostConstruct
    protected void init() {
        pattern = Pattern.compile("(.{20})(.{20})(.{20})(PGM / RUN BY / DATE)");
    }

    @Override
    public boolean parse(String line) {

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

package rinex.model.observations.header;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@Component("PGM / RUN BY / DATE")
public @Data class PgmRunByDate extends AbstractHeaderLabel {

    private String program;
    private String agency;
    private String created;

    @PostConstruct
    protected void init() {
        pattern = Pattern.compile("(.{20})(.{20})(.{20})(PGM / RUN BY / DATE)");
    }

    @Override
    public Boolean parse(String line) {

        Matcher matcher = pattern.matcher(line);

        Boolean isFind = matcher.find();
        program = isFind ? matcher.group(1).trim() : "";
        agency = isFind ? matcher.group(2).trim() : "";
        created = isFind ? matcher.group(3).trim() : "";
        return isFind;
    }
}

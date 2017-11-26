package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("PGM / RUN BY / DATE")
public @Data class PgmRunByDate implements HeaderLabel {

    private String program;
    private String agency;
    private String created;

    private Pattern pattern = Pattern.compile("(.{20})(.{20})(.{20})PGM / RUN BY / DATE");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseThreeParams(pattern, line);
        if (find) {
            List<String> params = parser.getParams();
            program = params.get(0);
            agency = params.get(1);
            created = params.get(2);
        }
        return find;
    }
}

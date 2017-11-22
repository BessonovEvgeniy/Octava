package rinex.model.observations.header;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("RINEX VERSION / TYPE")
public @Data class RinexVersionType implements HeaderLabel {

    @NonNull @Length(min = 4, max = 4, message = "Rinex version must have X.XX format")
    private String version;
    @NonNull @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode;

    private Pattern pattern = Pattern.compile("(\\s{1,5}\\d.\\d{1,2}\\s?)(.{30})([M,G,E,R])(.{19})(RINEX VERSION / TYPE)");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseTwoParams(pattern, line);
        if (find) {
            List<String> params = parser.getParams();
            version = params.get(0);
            mode = params.get(1);
        }
        return find;
    }
}
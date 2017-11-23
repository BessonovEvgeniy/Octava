package rinex.model.observations.header;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("RINEX VERSION / TYPE")
public @Getter class RinexVersionType implements HeaderLabel {

    @NonNull @Length(min = 4, max = 4, message = "Rinex version must have X.XX format")
    private String version;

    @NonNull
    @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode;

    private Pattern pattern = Pattern.compile("(\\s{1,5}\\d.\\d{1,2}\\s?)(.{30})([M,G,E,R,S])(.{19})(RINEX VERSION / TYPE)");

    public RinexVersionType() {}

    @Override
    public boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);

        boolean find = matcher.find();
        if (find) {
            version = matcher.group(1).trim();
            mode = matcher.group(3).trim();
        }
        return find;
    }

    @Override
    public String toString() {
        return "RinexVersionType {version=" + version  + ", mode=" + mode + '}';
    }
}
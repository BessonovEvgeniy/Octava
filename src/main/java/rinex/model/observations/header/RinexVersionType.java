package rinex.model.observations.header;

import lombok.Getter;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("RINEX VERSION / TYPE")
public @Getter class RinexVersionType implements HeaderLabel {

    @NotNull
    @Length(min = 4, max = 11, message = "Rinex version format is XXXXXXXX.XX format")
    private String version;

    @NotNull
    @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode;

    private Pattern pattern = Pattern.compile("\\s{5}(\\d{1,9}\\.\\d{1,2})\\s{4,11}[\\w,\\s]{20}([M,G,E,R,S]).{19}RINEX VERSION / TYPE");

    public RinexVersionType() {}

    @Override
    public boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);

        boolean find = matcher.find();
        if (find) {
            version = matcher.group(1).trim();
            mode = matcher.group(2).trim();
        }
        return find;
    }

    @Override
    public String toString() {
        return "RinexVersionType {version=" + version  + ", mode=" + mode + '}';
    }
}
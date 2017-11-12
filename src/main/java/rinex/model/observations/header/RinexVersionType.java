package rinex.model.observations.header;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@Component("RINEX VERSION / TYPE")
public @Data class RinexVersionType extends AbstractHeaderLabel {

    @NonNull @Length(min = 4, max = 4, message = "Rinex version must have X.XX format")
    private String version;
    @NonNull @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode;

    public RinexVersionType() {
        init();
    }

    private void init() {
        pattern = Pattern.compile("(\\s{1,5}\\d.\\d{1,2}\\s?)(.{30})([M,G,E,R])(.{19})(RINEX VERSION / TYPE)");
    }

    @Override
    public Boolean parse(String line) throws RinexHeaderException {
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            version = matcher.group(1).trim();
            mode = matcher.group(3).trim();
        } else {
            throw new RinexHeaderException(this.getClass().getName());
        }
        return true;
    }
}
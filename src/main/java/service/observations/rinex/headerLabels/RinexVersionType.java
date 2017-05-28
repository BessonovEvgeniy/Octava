package service.observations.rinex.headerLabels;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class RinexVersionType implements HeaderLabel {

    @NonNull @Length(min = 4, max = 4, message = "Rinex version must have X.XX format")
    private String version;
    private String mode;

    public RinexVersionType(){}

    @Override
    public boolean parse(String line) {

        Pattern pattern = Pattern.compile("\\d\\.\\d{2}");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            version = matcher.group().trim();
        }

        pattern = Pattern.compile(" .[G,R,S,T,M]{1} ");
        matcher = pattern.matcher(line);
        if (matcher.find()) {
            mode = matcher.group().trim();
        }

        return (!version.isEmpty() && !version.isEmpty());
    }
}

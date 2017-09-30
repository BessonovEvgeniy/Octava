package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class RinexVersionType extends AbstractHeaderLabel {

    @NonNull @Length(min = 4, max = 4, message = "Rinex version must have X.XX format")
    private String version;
    @NonNull @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode;

    public RinexVersionType() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("(\\s{1,5}\\d{1}.\\d{1,2}\\s{0,1})(.{30})([M,G,E,R]{1})(.{19})(RINEX VERSION / TYPE)");
    }

    @Override
    public Boolean parse(String line) throws RinexHeaderException {
        Matcher matcher = pattern.matcher(line);
        boolean isFind = matcher.find();
        if (isFind) {
            version = matcher.group(1).trim();
            mode = matcher.group(3).trim();
        } else {
            throw new RinexHeaderException(this.getClass().getName());
        }
        return isFind;
    }
}

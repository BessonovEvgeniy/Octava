package service.observations.rinex.rinexImpl.header;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import service.observations.rinex.Proccess;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class RinexVersionType implements Proccess {

    @NonNull @Length(min = 4, max = 4, message = "Rinex version must have X.XX format")
    private String version;
    @NonNull @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode;

    public RinexVersionType(){}

    @Override
    public boolean parse(String line) {
        Pattern pattern = Pattern.compile("(\\s{1,5}\\d{1}.\\d{1,2}\\s{0,1})(.{30})([M,G,E,R]{1})(.{19})(RINEX VERSION / TYPE)");
        Matcher matcher = pattern.matcher(line);
        boolean isFind = matcher.find();
        if (isFind) {
            version = matcher.group(1).trim();
            mode = matcher.group(3).trim();
        } else {
            version = null;
            mode = null;
        }
        return isFind;
    }
}

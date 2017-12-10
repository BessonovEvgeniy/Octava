package rinex.model.observations.header.impl;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import rinex.model.observations.header.HeaderLabel;

import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

public @Getter class RinexVersionType implements HeaderLabel {

    @NotNull
    @Length(min = 4, max = 11, message = "Rinex version format is XXXXXXXX.XX format")
    private String version;

    @NotNull
    @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode;

    private Pattern pattern = Pattern.compile("\\s{5}(\\d{1,9}\\.\\d{1,2})\\s{4,11}[\\w,\\s]{20}([M,G,E,R,S]).{19}RINEX VERSION / TYPE");

    public RinexVersionType(String version, String mode) {
        this.version = version;
        this.mode = mode;
    }

    private RinexVersionType() {}

    public static final RinexVersionType NULL = new RinexVersionType.NullRinexVersionType();

    private static class NullRinexVersionType extends RinexVersionType {
        @Override
        public String toString() {
            return "NullRinexVersionType";
        }
    }
}
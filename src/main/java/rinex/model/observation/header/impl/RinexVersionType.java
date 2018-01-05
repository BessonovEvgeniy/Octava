package rinex.model.observation.header.impl;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import rinex.model.observation.header.HeaderLabel;

import javax.validation.constraints.NotNull;

public @Getter class RinexVersionType implements HeaderLabel {

    @NotNull
    @Length(min = 4, max = 11, message = "Rinex version format is XXXXXXXX.XX format")
    private String version;

    @NotNull
    @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode;

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
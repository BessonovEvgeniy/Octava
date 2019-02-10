package model.observation.header.impl;

import model.observation.header.HeaderLabel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RinexVersionType implements HeaderLabel {

    public static final RinexVersionType NULL = new RinexVersionType.NullRinexVersionType();

    @NotNull
    @Length(min = 4, max = 11, message = "Rinex version format is XXXXXXXX.XX format")
    private String version = EMPTY_STRING;

    @NotNull
    @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode = EMPTY_STRING;

    public RinexVersionType(String version, String mode) {
        this.version = version;
        this.mode = mode;
    }

    private RinexVersionType() {}

    public String getVersion() {
        return version;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "Rinex ver. " + version + " mode " + mode;
    }

    private static class NullRinexVersionType extends RinexVersionType {
        @Override
        public String toString() {
            return "NullRinexVersionType";
        }
    }
}
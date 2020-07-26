package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RINEX_VERSION_TYPES")
public @Data class RinexVersionTypeModel extends BaseModel implements HeaderLabel {

    @Transient
    public static final RinexVersionTypeModel NULL = new RinexVersionTypeModel.NullRinexVersionTypeModel();

    @NotNull
    @Length(min = 4, max = 11, message = "Rinex version format is XXXXXXXX.XX format")
    private String version = EMPTY_STRING;

    @NotNull
    @Length(min = 1, max = 1, message = "Rinex mode must have 'X' format")
    private String mode = EMPTY_STRING;

    public RinexVersionTypeModel(String version, String mode) {
        this.version = version;
        this.mode = mode;
    }

    protected RinexVersionTypeModel() {}

    @Override
    public String toString() {
        return "Rinex ver. " + version + " mode " + mode;
    }

    private static class NullRinexVersionTypeModel extends RinexVersionTypeModel {
        @Override
        public String toString() {
            return "NullRinexVersionType";
        }
    }
}
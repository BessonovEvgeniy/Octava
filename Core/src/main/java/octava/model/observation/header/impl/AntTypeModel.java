package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ANT_TYPES")
public @Data class AntTypeModel extends BaseModel implements HeaderLabel {

    @Transient
    public static final AntTypeModel NULL = new NullAntTypeModel();

    private String antennaNumber = EMPTY_STRING;
    private String antennaType = EMPTY_STRING;

    protected AntTypeModel() {}

    public AntTypeModel(String antennaNumber, String antennaType) {
        this.antennaNumber = antennaNumber;
        this.antennaType = antennaType;
    }

    @Override
    public String toString() {
        return "Antenna Number: " + antennaNumber + " Antenna Type: " + antennaType;
    }

    private static class NullAntTypeModel extends AntTypeModel {
        @Override
        public String toString() {
            return "NullAntType";
        }
    }
}

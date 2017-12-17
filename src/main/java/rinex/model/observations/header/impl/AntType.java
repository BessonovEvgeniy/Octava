package rinex.model.observations.header.impl;

import lombok.Data;
import rinex.model.observations.header.HeaderLabel;

public @Data class AntType implements HeaderLabel {

    private String antennaNumber;

    private String antennaType;

    private AntType() {}

    public AntType(String antennaNumber, String antennaType) {
        this.antennaNumber = antennaNumber;
        this.antennaType = antennaType;
    }

    public static final AntType NULL = new AntType.NullAntType();

    private static class NullAntType extends AntType {
        @Override
        public String toString() {
            return "NullAntType";
        }
    }

}

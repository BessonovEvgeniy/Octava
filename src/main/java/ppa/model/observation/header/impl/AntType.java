package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

public @Data class AntType implements HeaderLabel {

    public static final AntType NULL = new AntType.NullAntType();

    private String antennaNumber = EMPTY_STRING;
    private String antennaType = EMPTY_STRING;

    private AntType() {}

    public AntType(String antennaNumber, String antennaType) {
        this.antennaNumber = antennaNumber;
        this.antennaType = antennaType;
    }

    @Override
    public String toString() {
        return "Antenna Number: " + antennaNumber + " Antenna Type: " + antennaType;
    }

    private static class NullAntType extends AntType {
        @Override
        public String toString() {
            return "NullAntType";
        }
    }
}

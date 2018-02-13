package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

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

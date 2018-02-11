package rinex.model.observation.header.impl;

import lombok.Data;
import rinex.model.observation.header.HeaderLabel;

public @Data class OfSatellites implements HeaderLabel {

    int numberOfSatellites = 0;

    protected OfSatellites(){}

    public OfSatellites(int ofSats){
        numberOfSatellites = ofSats;
    }

    public static final OfSatellites NULL = new OfSatellites.NullOfSatellites();

    private static class NullOfSatellites extends OfSatellites {
        @Override
        public String toString() {
            return "NullOfSatellites";
        }
    }
}

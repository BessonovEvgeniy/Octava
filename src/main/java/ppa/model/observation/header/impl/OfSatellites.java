package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

public @Data class OfSatellites implements HeaderLabel {

    public static final OfSatellites NULL = new OfSatellites.NullOfSatellites();

    int numberOfSatellites = 0;

    protected OfSatellites(){}

    public OfSatellites(int ofSats){
        numberOfSatellites = ofSats;
    }

    @Override
    public String toString() {
        return "OfSatellites " + numberOfSatellites;
    }

    private static class NullOfSatellites extends OfSatellites {
        @Override
        public String toString() {
            return "NullOfSatellites";
        }
    }
}

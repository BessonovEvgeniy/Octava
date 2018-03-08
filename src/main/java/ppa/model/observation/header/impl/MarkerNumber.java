package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

public @Data class MarkerNumber implements HeaderLabel {

    public static final MarkerNumber NULL = new MarkerNumber.NullMarkerNumber();

    private String markerNumber;

    private MarkerNumber() {}

    public MarkerNumber(String markerNumber) {
        this.markerNumber = markerNumber;
    }

    private static class NullMarkerNumber extends MarkerNumber {
        @Override
        public String toString() {
            return "NullMarkerNumber";
        }
    }
}

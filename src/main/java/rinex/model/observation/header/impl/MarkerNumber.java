package rinex.model.observation.header.impl;

import lombok.Data;
import rinex.model.observation.header.HeaderLabel;

public @Data class MarkerNumber implements HeaderLabel {

    private String markerNumber;

    private MarkerNumber() {}

    public MarkerNumber(String markerNumber) {
        this.markerNumber = markerNumber;
    }

    public static final MarkerNumber NULL = new MarkerNumber.NullMarkerNumber();

    private static class NullMarkerNumber extends MarkerNumber {
        @Override
        public String toString() {
            return "NullMarkerNumber";
        }
    }
}

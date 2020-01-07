package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.observation.header.HeaderLabel;

public @Data class MarkerNumber implements HeaderLabel {

    public static final MarkerNumber NULL = new MarkerNumber.NullMarkerNumber();

    private String markerNumber = EMPTY_STRING;

    private MarkerNumber() {}

    public MarkerNumber(String markerNumber) {
        this.markerNumber = markerNumber;
    }

    @Override
    public String toString() {
        return "MarkerNumber " + markerNumber;
    }

    private static class NullMarkerNumber extends MarkerNumber {
        @Override
        public String toString() {
            return "NullMarkerNumber";
        }
    }
}

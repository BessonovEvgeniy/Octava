package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.observation.header.HeaderLabel;

public @Data class MarkerName implements HeaderLabel {

    public static final MarkerName NULL = new MarkerName.NullMarkerName();

    private String markerName = EMPTY_STRING;

    private MarkerName() {}

    public MarkerName(String markerName) {
        this.markerName = markerName;
    }

    @Override
    public String toString() {
        return "MarkerName " + markerName;
    }

    private static class NullMarkerName extends MarkerName {
        @Override
        public String toString() {
            return "NullMarkerName";
        }
    }
}

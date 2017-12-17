package rinex.model.observations.header.impl;

import lombok.Data;
import rinex.model.observations.header.HeaderLabel;

public @Data class MarkerName implements HeaderLabel {

    private String markerName;

    private MarkerName() {}

    public MarkerName(String markerName) {
        this.markerName = markerName;
    }

    public static final MarkerName NULL = new MarkerName.NullMarkerName();

    private static class NullMarkerName extends MarkerName {
        @Override
        public String toString() {
            return "NullMarkerName";
        }
    }
}

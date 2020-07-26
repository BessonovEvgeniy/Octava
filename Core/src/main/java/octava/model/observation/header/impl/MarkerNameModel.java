package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MARKER_NAMES")
public @Data class MarkerNameModel extends BaseModel implements HeaderLabel {

    @Transient
    public static final MarkerNameModel NULL = new NullMarkerNameModel();

    private String markerName = EMPTY_STRING;

    protected MarkerNameModel() {}

    public MarkerNameModel(String markerName) {
        this.markerName = markerName;
    }

    @Override
    public String toString() {
        return "MarkerName " + markerName;
    }

    private static class NullMarkerNameModel extends MarkerNameModel {
        @Override
        public String toString() {
            return "NullMarkerName";
        }
    }
}

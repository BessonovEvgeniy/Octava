package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

public @Data class ObserverAgency implements HeaderLabel {

    public static final ObserverAgency NULL = new ObserverAgency.NullObserverAgency();

    private String observerName = EMPTY_STRING;
    private String agencyName = EMPTY_STRING;

    private ObserverAgency() {}

    public ObserverAgency(String observerName, String agencyName) {
        this.observerName = observerName;
        this.agencyName = agencyName;
    }

    @Override
    public String toString() {
        return "Observer Name=" + observerName + " Agency Name=" + agencyName;
    }

    private static class NullObserverAgency extends ObserverAgency {
        @Override
        public String toString() {
            return "NullObserverAgency";
        }
    }
}

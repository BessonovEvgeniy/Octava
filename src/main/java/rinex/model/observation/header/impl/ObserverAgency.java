package rinex.model.observation.header.impl;

import lombok.Data;
import rinex.model.observation.header.HeaderLabel;

public @Data class ObserverAgency implements HeaderLabel {

    private String observerName;

    private String agencyName;

    private ObserverAgency() {}

    public ObserverAgency(String observerName, String agencyName) {
        this.observerName = observerName;
        this.agencyName = agencyName;
    }

    public static final ObserverAgency NULL = new ObserverAgency.NullObserverAgency();

    private static class NullObserverAgency extends ObserverAgency {
        @Override
        public String toString() {
            return "NullObserverAgency";
        }
    }
}

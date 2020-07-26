package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OBSERVER_AGENCIES")
public @Data class ObserverAgencyModel extends BaseModel implements HeaderLabel {

    public static final ObserverAgencyModel NULL = new NullObserverAgencyModel();

    private String observerName = EMPTY_STRING;
    private String agencyName = EMPTY_STRING;

    protected ObserverAgencyModel() {}

    public ObserverAgencyModel(final String observerName, final String agencyName) {
        this.observerName = observerName;
        this.agencyName = agencyName;
    }

    @Override
    public String toString() {
        return "Observer Name=" + observerName + " Agency Name=" + agencyName;
    }

    private static class NullObserverAgencyModel extends ObserverAgencyModel {
        @Override
        public String toString() {
            return "NullObserverAgency";
        }
    }
}

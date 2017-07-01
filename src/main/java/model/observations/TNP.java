package model.observations;

import lombok.Data;
import model.rinex.*;

import java.util.Map;
import java.util.Set;

public @Data class TNP implements GPS, GLONASS {

    private enum GNSS {
        GPS, GLONASS
    }

    private Header header;

    private Map<GNSS, Set<Observations>> obs;

    private Set<Double> epoch;

    @Override
    public void addEpoch(Double epoch) {
        this.epoch.add(epoch);
    }
}

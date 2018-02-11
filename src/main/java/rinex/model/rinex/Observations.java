package rinex.model.rinex;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rinex.model.BaseModel;
import rinex.model.observation.header.impl.ObsType;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
public @Data class Observations extends BaseModel implements Gnss {

    protected ObsType obsType;

    private Map<LocalDateTime, double[]> obs = new LinkedHashMap<>();

    public Observations(ObsType type) {
        obsType = type;
    }

    public double[] getEpoch(LocalDateTime epochTime) {
        return obs.get(epochTime);
    }

    public void upsertEpoch(LocalDateTime epochTime, double[] epochData) {
        validateEpochData(epochData);
        obs.put(epochTime, epochData);
    }

    private void validateEpochData(double[] epochData) {
        if (epochData.length != Gnss.MAX_SAT) {
            throw new IllegalStateException("Excpected " + Gnss.MAX_SAT + " satellites. But found " + epochData.length + ". Epoch data " + epochData);
        }
    }
}

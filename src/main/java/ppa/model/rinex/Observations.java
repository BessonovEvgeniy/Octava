package ppa.model.rinex;

import Jama.Matrix;
import lombok.Data;
import lombok.EqualsAndHashCode;
import business.model.BaseModel;
import ppa.model.observation.header.impl.ObsType;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
public @Data class Observations extends BaseModel implements Gnss {

    protected ObsType obsType;

    private Map<LocalDateTime, Matrix> obs = new LinkedHashMap<>();

    private Map<LocalDateTime, Integer> flags = new LinkedHashMap<>();

    public int size() {
        return obs.size();
    }

    public Observations(ObsType type) {
        obsType = type;
    }

    public Matrix getEpoch(LocalDateTime epochTime) {
        return obs.get(epochTime);
    }

    public void putEpoch(LocalDateTime epochTime, Matrix epochData) {
        validateEpochData(epochData);
        obs.put(epochTime, epochData);
    }

    public void putFlag(LocalDateTime epochTime, int flag) {
        flags.put(epochTime, flag);
    }

    private void validateEpochData(Matrix epochData) {
        if (epochData.getColumnDimension() != Gnss.MAX_SAT) {
            throw new IllegalStateException("Excpected " + Gnss.MAX_SAT + " satellites. But found " + epochData.getColumnDimension() + ". Epoch data " + epochData);
        }
    }
}

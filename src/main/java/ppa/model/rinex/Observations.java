package ppa.model.rinex;

import business.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import ppa.model.observation.header.impl.ObsType;
import utils.time.Sections;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
public @Data class Observations extends BaseModel implements Gnss {

    protected ObsType obsType;

    private RealMatrix fullSizeMatrix;

    private Map<LocalDateTime, RealMatrix> obsMatrix = new LinkedHashMap<>();

    private Map<LocalDateTime, Double> obs = new LinkedHashMap<>();

    private Map<LocalDateTime, Integer> flags = new LinkedHashMap<>();

    private Sections.SectionsData sectionsData;

    public int size() {
        return obs.size();
    }

    public Observations(ObsType type) {
        obsType = type;
    }

    public void buildFullSizeMatrix() {
        fullSizeMatrix = new Array2DRowRealMatrix(obsMatrix.size(), Gnss.MAX_SAT);
        int counter = 0;
        for (Map.Entry<LocalDateTime, RealMatrix> rinexRow : obsMatrix.entrySet()) {
            fullSizeMatrix.setRowMatrix(counter, rinexRow.getValue());
            counter++;
        }
    }

    public RealMatrix getFullSizeMatrix () {
        if (fullSizeMatrix == null || obsMatrix.size() != fullSizeMatrix.getColumnDimension()) {
            buildFullSizeMatrix();
        }
        return fullSizeMatrix;
    }

    public RealMatrix getEpoch(LocalDateTime epochTime) {
        return obsMatrix.get(epochTime);
    }

    public void putEpoch(LocalDateTime epochTime, RealMatrix epochData) {
        validateEpochData(epochData);
        synchronized (obsMatrix) {
            obsMatrix.put(epochTime, epochData);
        }
    }

    public void putFlag(LocalDateTime epochTime, int flag) {
        flags.put(epochTime, flag);
    }

    private void validateEpochData(RealMatrix epochData) {
        if (epochData.getColumnDimension() != Gnss.MAX_SAT) {
            throw new IllegalStateException("Expected " + Gnss.MAX_SAT + " satellites. But found " + epochData.getColumnDimension() + ". Epoch data " + epochData);
        }
    }

    @Override
    public String toString() {
        return "";
    }
}

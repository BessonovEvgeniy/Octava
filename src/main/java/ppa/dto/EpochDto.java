package ppa.dto;


import lombok.Data;
import org.apache.commons.lang.ArrayUtils;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public @Data class EpochDto {

    private LocalDateTime epochTime;

    private double gpsSeconds;

    private int flag;

    private int numSv;

    private int numTypesOfObs;

    private List<String> satellites;

    private Map<String, String> rawEpochData = new LinkedHashMap<>();

    private Map<String, double[]> epochData = new LinkedHashMap<>();

    public EpochDto() {}

    public EpochDto(LocalDateTime epochTime) {
        this.epochTime = epochTime;
    }

    public void setRawEpochData(Map<String, String> rawEpochData) {
        this.rawEpochData = rawEpochData;
    }

    public void setParsedEpochData(Map<String, double[]> parsedEpochData) {
        epochData.putAll(parsedEpochData);
    }

    public double getEpochData(int index, String svCode) {
        double[] parsedObs = epochData.get(svCode);
        if (ArrayUtils.isNotEmpty(parsedObs)) {
            return index > parsedObs.length - 1 ? parsedObs[index] : 0;
        } else {
            return 0;
        }
    }
}

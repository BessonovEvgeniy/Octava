package ppa.dto;


import lombok.Data;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
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
        return ArrayUtils.isNotEmpty(parsedObs) && index > parsedObs.length - 1 ? 0 : parsedObs[index];
    }
}

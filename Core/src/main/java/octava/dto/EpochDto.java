package octava.dto;


import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class EpochDto {

    private LocalDateTime epochTime;

    private double gpsSeconds;

    private int flag;

    private int numSv;

    private int numTypesOfObs;

    private List<String> satellites;

    private Map<String, String> rawEpochData = Collections.emptyMap();

    private Map<String, double[]> epochData = Collections.emptyMap();

    public EpochDto() {}

    public EpochDto(LocalDateTime epochTime) {
        this.epochTime = epochTime;
    }

    public void setRawEpochData(Map<String, String> rawEpochData) {
        this.rawEpochData = rawEpochData;
    }

    public void setParsedEpochData(Map<String, double[]> parsedEpochData) {
        this.epochData = parsedEpochData;
    }

    public double getEpochData(int index, String svCode) {
        double[] parsedObs = epochData.get(svCode);
        return ArrayUtils.isNotEmpty(parsedObs) && index > parsedObs.length - 1 ? 0 : parsedObs[index];
    }

    public LocalDateTime getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(LocalDateTime epochTime) {
        this.epochTime = epochTime;
    }

    public double getGpsSeconds() {
        return gpsSeconds;
    }

    public void setGpsSeconds(double gpsSeconds) {
        this.gpsSeconds = gpsSeconds;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getNumSv() {
        return numSv;
    }

    public void setNumSv(int numSv) {
        this.numSv = numSv;
    }

    public int getNumTypesOfObs() {
        return numTypesOfObs;
    }

    public void setNumTypesOfObs(int numTypesOfObs) {
        this.numTypesOfObs = numTypesOfObs;
    }

    public List<String> getSatellites() {
        return satellites;
    }

    public void setSatellites(List<String> satellites) {
        this.satellites = satellites;
    }

    public Map<String, String> getRawEpochData() {
        return rawEpochData;
    }

    public Map<String, double[]> getEpochData() {
        return epochData;
    }

    public void setEpochData(Map<String, double[]> epochData) {
        this.epochData = epochData;
    }
}

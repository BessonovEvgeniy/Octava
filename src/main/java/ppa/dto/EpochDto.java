package ppa.dto;


import com.google.common.primitives.Doubles;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public @Data class EpochDto {

    private LocalDateTime epochTime;

    private double gpsSeconds;

    private int flag;

    private int numSv;

    private List<String> satellites;

    @Setter(AccessLevel.NONE)
    private Map<String, String> rawEpochData = new LinkedHashMap<>();

    @Getter(AccessLevel.NONE)
    private Map<String, List<Double>> epochData;

    public EpochDto() {}

    public EpochDto(LocalDateTime epochTime) {
        this.epochTime = epochTime;
    }

    public void add(Map<String, String> rawEpochData) {
        this.rawEpochData.putAll(rawEpochData);
    }

    public void setRawEpochData(Map<String, String> rawEpochData) {
        this.rawEpochData = rawEpochData;
        this.epochData = null;
    }

    public void addRawEpochData(Map<String, String> rawEpochData) {
        this.rawEpochData.putAll(rawEpochData);
        this.epochData = null;
    }

    public Map<String, List<Double>> getEpochData() {
        if (epochData == null) {
            epochData = new LinkedHashMap<>();
            for (Map.Entry<String, String> item : rawEpochData.entrySet()) {
                String rawEpochLine = item.getValue();
                String[] obs = rawEpochLine.trim().split("    |   |  ");
                List<Double> epochData = Arrays.stream(obs).map(str -> Doubles.tryParse(str.replaceAll(" ","0"))).collect(Collectors.toList());
                this.epochData.put(item.getKey(), epochData);
            }
        }
        return epochData;
    }

    public double getEpochData(int index, String svCode) {
        List<Double> epochData = getEpochData().get(svCode);
        if (CollectionUtils.isNotEmpty(epochData)) {
            Double epochValue = epochData.get(index);
            if (epochValue == null) {
                return 0;
            } else {
                return epochValue;
            }
        } else {
            return 0;
        }
    }
}

package model.rinex;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.TreeMap;

@MappedSuperclass
public @Data abstract class Observations extends BaseModel{

    protected Integer sv;

    protected TreeMap<Integer, Double> timeVsObservations;
}

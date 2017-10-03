package rinex.model.rinex;

import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Map;

@MappedSuperclass
public @Data abstract class Observations extends BaseModel {

    protected Integer sv;

    protected Map<Integer, List<Double>> obs;

    public enum System {
        GPS, GLONASS, GALILEO;
    }

    public enum Type {
        C, P, L, D, S;
    }
}

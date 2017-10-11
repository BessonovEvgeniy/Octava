package rinex.model.rinex;

import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Map;

public @Data class Observations extends BaseModel implements Gnss {

    protected GnssSystem system;

    protected Type obsType;

    List<double[]> obs;

    public Observations(GnssSystem sys, Type type) {
        system = sys;
        obsType = type;
    }

    public enum GnssSystem {
        GPS("G"), GLONASS("R"), GALILEO("E");

        String code;

        GnssSystem(String sysCode) {
            code = sysCode;
        }

        public String getCode() {
            return code;
        }
    }

    public void add(Observations observations) throws Exception {
        add(observations.getObs());
    }

    public void add(List<double[]> observations) throws Exception {
        observations.forEach(array -> {
            if (array.length != MAX_GPS_SAT) {
                System.out.println("Warning. You are trying to add array not proper length: " + MAX_GPS_SAT);
            }
        });

        obs.addAll(observations);
    }

    public enum Type {
        C, P, L, D, S;
    }
}

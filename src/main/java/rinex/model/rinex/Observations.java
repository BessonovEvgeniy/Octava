package rinex.model.rinex;

import lombok.Data;
import rinex.model.BaseModel;
import rinex.service.Impl.observations.rinex.rinexImpl.header.TypesOfObs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public @Data class Observations extends BaseModel implements Gnss {

    protected TypesOfObs.Type obsType;

    List<double[]> obs = new LinkedList<>();

    public Observations(TypesOfObs.Type type) {
        obsType = type;
    }

    public List<double[]> getObs() {
        //Prevent editing Obs directly
        return new LinkedList<>(obs);
    }

    public void add(Observations observations) throws Exception {
        add(observations.getObs());
    }

    public void add(double[] observations) throws Exception {
        obs.add(observations);
    }

    public void add(List<double[]> observations) throws Exception {
        observations.forEach(array -> {
            if (array.length != MAX_SAT) {
                StringBuilder str = new StringBuilder();
                Arrays.stream(array).forEach(value -> str.append(value + " "));
                System.out.println("Warning. You are trying to add array not proper length: " + MAX_SAT + ".\n" +
                        str.toString() + ". Epoch will be skipped.");
            } else {
                obs.add(array);
            }
        });
    }
}

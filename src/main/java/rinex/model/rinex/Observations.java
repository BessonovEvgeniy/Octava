package rinex.model.rinex;

import lombok.Data;
import lombok.EqualsAndHashCode;
import rinex.model.BaseModel;
import rinex.model.observations.header.TypesOfObs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
public @Data class Observations extends BaseModel implements Gnss {

    protected TypesOfObs.Type obsType;

    List<double[]> obs = new LinkedList<>();

    public Observations(TypesOfObs.Type type) {
        obsType = type;
    }

    private List<double[]> getObs() {
        //Prevent editing Obs directly
        return new LinkedList<>(obs);
    }

    public void add(Observations observations) throws Exception {
        add(observations.getObs());
    }

    public void add(double[] observations) {
        obs.add(observations);
    }

    private void add(List<double[]> observations) {
        observations.forEach(array -> {
            if (array.length != MAX_SAT) {
                StringBuilder str = new StringBuilder();
                Arrays.stream(array).forEach(value -> str.append(value).append(" "));
                System.out.println("Warning. You are trying to add array not proper length: " + MAX_SAT + ".\n" +
                        str.toString() + ". Epoch will be skipped.");
            } else {
                obs.add(array);
            }
        });
    }
}

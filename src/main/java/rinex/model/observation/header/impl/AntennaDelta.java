package rinex.model.observation.header.impl;

import lombok.Data;
import rinex.model.observation.header.HeaderLabel;

public @Data class AntennaDelta implements HeaderLabel {

    private double delH;

    private double delE;

    private double delN;

    private AntennaDelta() {}

    public AntennaDelta(double delH, double delE, double delN) {
        this.delH = delH;
        this.delE = delE;
        this.delN = delN;
    }

    public static final AntennaDelta NULL = new AntennaDelta.NullAntennaDelta();

    private static class NullAntennaDelta extends AntennaDelta {
        @Override
        public String toString() {
            return "NullAntennaDelta";
        }
    }
}
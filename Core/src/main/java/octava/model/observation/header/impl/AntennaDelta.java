package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.observation.header.HeaderLabel;

public @Data class AntennaDelta implements HeaderLabel {

    public static final AntennaDelta NULL = new AntennaDelta.NullAntennaDelta();

    private double delH;

    private double delE;

    private double delN;

    private AntennaDelta() {}

    public AntennaDelta(double delH, double delE, double delN) {
        this.delH = delH;
        this.delE = delE;
        this.delN = delN;
    }

    @Override
    public String toString() {
        return "AntennaDelta delH=" + delH + " delE=" + delE + " delN=" + delN;
    }

    private static class NullAntennaDelta extends AntennaDelta {
        @Override
        public String toString() {
            return "NullAntennaDelta";
        }
    }
}
package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ANTENNA_DELTAS")
public @Data class AntennaDeltaModel extends BaseModel implements HeaderLabel {

    @Transient
    public static final AntennaDeltaModel NULL = new NullAntennaDeltaModel();

    private double delH;

    private double delE;

    private double delN;

    protected AntennaDeltaModel() {}

    public AntennaDeltaModel(double delH, double delE, double delN) {
        this.delH = delH;
        this.delE = delE;
        this.delN = delN;
    }

    @Override
    public String toString() {
        return "AntennaDelta delH=" + delH + " delE=" + delE + " delN=" + delN;
    }

    private static class NullAntennaDeltaModel extends AntennaDeltaModel {
        @Override
        public String toString() {
            return "NullAntennaDelta";
        }
    }
}
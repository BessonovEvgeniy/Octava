package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "APPROXIMATE_POSITIONS")
public @Data class ApproxPosModel extends BaseModel implements HeaderLabel {

    @Transient
    public static final ApproxPosModel NULL = new NullApproxPosModel();

    private double x;
    private double y;
    private double z;

    protected ApproxPosModel() {}

    public ApproxPosModel(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "Approx position X=" + x + " Y=" + y + " Z=" + z;
    }

    private static class NullApproxPosModel extends ApproxPosModel {
        @Override
        public String toString() {
            return "NullApproxPos";
        }
    }
}

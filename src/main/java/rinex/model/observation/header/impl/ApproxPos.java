package rinex.model.observation.header.impl;

import lombok.Data;
import rinex.model.observation.header.HeaderLabel;

public @Data class ApproxPos implements HeaderLabel {

    private double x;

    private double y;

    private double z;

    private ApproxPos() {}

    public ApproxPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static final ApproxPos NULL = new ApproxPos.NullApproxPos();

    private static class NullApproxPos extends ApproxPos {
        @Override
        public String toString() {
            return "NullApproxPos";
        }
    }
}

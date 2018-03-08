package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

public @Data class ApproxPos implements HeaderLabel {

    public static final ApproxPos NULL = new ApproxPos.NullApproxPos();

    private double x;
    private double y;
    private double z;

    private ApproxPos() {}

    public ApproxPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private static class NullApproxPos extends ApproxPos {
        @Override
        public String toString() {
            return "NullApproxPos";
        }
    }
}

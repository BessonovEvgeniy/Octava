package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

public @Data class Interval implements HeaderLabel {

    private double interval;

    private Interval() {}

    public Interval(double interval) {
        this.interval = interval;
    }

    public static final Interval NULL = new Interval.NullInterval();

    private static class NullInterval extends Interval {
        @Override
        public String toString() {
            return "NullInterval";
        }
    }
}

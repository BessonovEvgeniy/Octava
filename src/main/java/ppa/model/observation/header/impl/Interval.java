package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

public @Data class Interval implements HeaderLabel {

    public static final Interval NULL = new Interval.NullInterval();

    private double interval;

    private Interval() {}

    public Interval(double interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "Interval=" + interval;
    }

    private static class NullInterval extends Interval {
        @Override
        public String toString() {
            return "NullInterval";
        }
    }
}

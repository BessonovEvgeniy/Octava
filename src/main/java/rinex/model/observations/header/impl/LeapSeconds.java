package rinex.model.observations.header.impl;

import lombok.Data;
import rinex.model.observations.header.HeaderLabel;

public @Data class LeapSeconds implements HeaderLabel {

    private int leapSeconds;

    private LeapSeconds() {}

    public LeapSeconds(int leapSeconds) {
        this.leapSeconds = leapSeconds;
    }

    public static final LeapSeconds NULL = new LeapSeconds.NullLeapSeconds();

    private static class NullLeapSeconds extends LeapSeconds {
        @Override
        public String toString() {
            return "NullLeapSeconds";
        }
    }
}

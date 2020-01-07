package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.observation.header.HeaderLabel;

public @Data class LeapSeconds implements HeaderLabel {

    public static final LeapSeconds NULL = new LeapSeconds.NullLeapSeconds();

    private int leapSeconds;

    private LeapSeconds() {}

    public LeapSeconds(int leapSeconds) {
        this.leapSeconds = leapSeconds;
    }

    @Override
    public String toString() {
        return "Leap Seconds=" + leapSeconds;
    }

    private static class NullLeapSeconds extends LeapSeconds {
        @Override
        public String toString() {
            return "NullLeapSeconds";
        }
    }
}

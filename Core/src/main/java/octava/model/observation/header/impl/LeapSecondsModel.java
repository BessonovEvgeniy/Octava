package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "LEAP_SECONDS")
public @Data class LeapSecondsModel extends BaseModel implements HeaderLabel {

    @Transient
    public static final LeapSecondsModel NULL = new NullLeapSecondsModel();

    private int leapSeconds;

    protected LeapSecondsModel() {}

    public LeapSecondsModel(final int leapSeconds) {
        this.leapSeconds = leapSeconds;
    }

    @Override
    public String toString() {
        return "Leap Seconds=" + leapSeconds;
    }

    private static class NullLeapSecondsModel extends LeapSecondsModel {
        @Override
        public String toString() {
            return "NullLeapSeconds";
        }
    }
}

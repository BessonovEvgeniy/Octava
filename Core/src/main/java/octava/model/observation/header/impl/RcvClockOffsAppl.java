package octava.model.observation.header.impl;

import octava.model.observation.header.HeaderLabel;

public class RcvClockOffsAppl implements HeaderLabel {

    public static final RcvClockOffsAppl NULL = new RcvClockOffsAppl.NullRcvClockOffsAppl();

    private boolean receiverOffset = false;

    private RcvClockOffsAppl() {}

    public RcvClockOffsAppl(boolean receiverOffset) {
        this.receiverOffset = receiverOffset;
    }

    @Override
    public String toString() {
        return "Receiver Offset is " + receiverOffset;
    }

    private static class NullRcvClockOffsAppl extends RcvClockOffsAppl {
        @Override
        public String toString() {
            return "NullRcvClockOffsAppl";
        }
    }
}

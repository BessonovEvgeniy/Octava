package ppa.model.observation.header.impl;

import ppa.model.observation.header.HeaderLabel;

public class RcvClockOffsAppl implements HeaderLabel {

    public static final RcvClockOffsAppl NULL = new RcvClockOffsAppl.NullRcvClockOffsAppl();

    private boolean receiverOffset = false;

    private RcvClockOffsAppl() {}

    public RcvClockOffsAppl(boolean receiverOffset) {
        this.receiverOffset = receiverOffset;
    }

    private static class NullRcvClockOffsAppl extends RcvClockOffsAppl {
        @Override
        public String toString() {
            return "NullRcvClockOffsAppl";
        }
    }
}

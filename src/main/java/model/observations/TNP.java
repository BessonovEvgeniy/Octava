package model.observations;

import lombok.Data;
import model.rinex.*;

public @Data class TNP implements GPS, GLONASS {

    private Header header;
    private Observations obs;
    private Time time;

    public static final TNP NULL = new NullTNP();

    private static class NullTNP extends TNP {

        private Header header = new Header();
        private Time time = new Time();

        @Override
        public String toString() {
            return "Null TNP";
        }
    }
}

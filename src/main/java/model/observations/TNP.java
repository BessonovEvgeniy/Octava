package model.observations;

import lombok.Data;
import model.rinex.*;

public @Data class TNP implements GPS, GLONASS {

    private Header header;

    private Observations obs;

    private Time time;
}

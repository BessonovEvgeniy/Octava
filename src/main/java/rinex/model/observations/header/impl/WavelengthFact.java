package rinex.model.observations.header.impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import rinex.model.observations.header.HeaderLabel;

import java.util.Collection;

@Component("WAVELENGTH FACT L1/2")
public @Data class WavelengthFact implements HeaderLabel {

    private int fullCycle;

    private int halfCycle;

    private Collection<Integer> sats;

    private WavelengthFact() {}

    public WavelengthFact(int fullCycle, int halfCycle, Collection<Integer> sats) {
        this.fullCycle = fullCycle;
        this.halfCycle = halfCycle;
        this.sats = sats;
    }

    public static final WavelengthFact NULL = new WavelengthFact.NullWavelengthFact();

    private static class NullWavelengthFact extends WavelengthFact {
        @Override
        public String toString() {
            return "NullWavelengthFact";
        }
    }
}

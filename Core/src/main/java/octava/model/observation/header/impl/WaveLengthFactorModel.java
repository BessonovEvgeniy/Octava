package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "WAVE_LENGTH_FACTORS")
public @Data class WaveLengthFactorModel extends BaseModel implements HeaderLabel {

    public static final WaveLengthFactorModel NULL = new NullWaveLengthFactorModel();

    @NotNull
    private Ambiguities freq1 = Ambiguities.NoFreq;

    @NotNull
    private Ambiguities freq2 = Ambiguities.NoFreq;

    @NotNull
    private Ambiguities cycles = Ambiguities.NoFreq;

    @ElementCollection
    private Collection<String> satellites = new ArrayList<>();

    protected WaveLengthFactorModel() { }

    public WaveLengthFactorModel(int freqL1Param, int freqL2Param, Collection<String> satellites) {

        this.freq1 = Ambiguities.values()[freqL1Param];
        this.freq2 = Ambiguities.values()[freqL2Param];
        this.satellites.addAll(satellites);

        if (freq1.equals(Ambiguities.NoFreq)) {
            throw new IllegalStateException("L1 param can't be zero, empty or blank");
        }
    }

    public void add(WaveLengthFactorModel waveLengthFactorModel) {

    }

    public enum Ambiguities {
        NoFreq, FullCycle, HalfCycle;

        Ambiguities() {}
    }

    private static class NullWaveLengthFactorModel extends WaveLengthFactorModel {
        @Override
        public String toString() {
            return "NullWaveLengthFact";
        }
    }
}

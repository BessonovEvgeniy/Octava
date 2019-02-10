package model.observation.header.impl;

import lombok.Data;
import model.observation.header.HeaderLabel;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

public @Data class WaveLengthFact implements HeaderLabel {

    public static final WaveLengthFact NULL = new WaveLengthFact.NullWaveLengthFact();

    @NotNull
    private Ambiguities freq1 = Ambiguities.NoFreq;

    @NotNull
    private Ambiguities freq2 = Ambiguities.NoFreq;

    @NotNull
    private Ambiguities cycles = Ambiguities.NoFreq;

    private Collection<String> sats = new ArrayList<>();

    private WaveLengthFact() { }

    public WaveLengthFact(int freqL1Param, int freqL2Param, Collection<String> sats) {

        this.freq1 = Ambiguities.values()[freqL1Param];
        this.freq2 = Ambiguities.values()[freqL2Param];
        this.sats.addAll(sats);

        if (freq1.equals(Ambiguities.NoFreq)) {
            throw new IllegalStateException("L1 param can't be zero, empty or blank");
        }
    }

    public void add(WaveLengthFact waveLengthFact) {

    }

    public enum Ambiguities {
        NoFreq, FullCycle, HalfCycle;

        Ambiguities() {}
    }

    private static class NullWaveLengthFact extends WaveLengthFact {
        @Override
        public String toString() {
            return "NullWaveLengthFact";
        }
    }
}

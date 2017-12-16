package rinex.model.observations.header.impl;

import lombok.Data;
import rinex.model.observations.header.HeaderLabel;

import java.util.ArrayList;
import java.util.Collection;

public @Data class TypesOfObs implements HeaderLabel {

    private Collection<ObsType> obsTypes = new ArrayList<>();

    private TypesOfObs() {}

    public TypesOfObs(Collection<ObsType> obsTypes) {
        this.obsTypes = obsTypes;
    }

    public static final TypesOfObs NULL = new TypesOfObs.NullTypesOfObs();

    private static class NullTypesOfObs extends TypesOfObs {
        @Override
        public String toString() {
            return "NullTypesOfObs";
        }
    }
}

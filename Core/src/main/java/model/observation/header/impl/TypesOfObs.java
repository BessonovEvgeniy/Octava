package model.observation.header.impl;

import lombok.Data;
import model.observation.header.HeaderLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public @Data class TypesOfObs implements HeaderLabel {

    public static final TypesOfObs NULL = new TypesOfObs.NullTypesOfObs();

    private List<ObsType> obsTypes = new ArrayList<>();

    private TypesOfObs() {}

    public TypesOfObs(List<ObsType> obsTypes) {
        this.obsTypes = obsTypes;
    }

    public int size(){
        return obsTypes.size();
    }

    public ObsType get(int index) {
        return obsTypes.get(index);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add("Observation Types");
        obsTypes.stream().forEachOrdered(obsType -> joiner.add(obsType.toString()));
        return joiner.toString();
    }

    private static class NullTypesOfObs extends TypesOfObs {
        @Override
        public String toString() {
            return "NullTypesOfObs";
        }
    }
}

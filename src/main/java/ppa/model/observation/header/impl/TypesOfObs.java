package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

import java.util.ArrayList;
import java.util.List;

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

    private static class NullTypesOfObs extends TypesOfObs {
        @Override
        public String toString() {
            return "NullTypesOfObs";
        }
    }
}

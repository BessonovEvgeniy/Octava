package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static org.apache.commons.lang3.StringUtils.SPACE;

@Entity
@Table(name = "TYPES_OF_OBSERVATIONS")
public @Data class TypesOfObsModel extends BaseModel implements HeaderLabel {

    @Transient
    public static final TypesOfObsModel NULL = new NullTypesOfObsModel();

    @ElementCollection
    private List<ObsType> obsTypes = new ArrayList<>();

    protected TypesOfObsModel() {}

    public TypesOfObsModel(List<ObsType> obsTypes) {
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
        StringJoiner joiner = new StringJoiner(SPACE);
        joiner.add("Observation Types");
        obsTypes.stream().forEachOrdered(obsType -> joiner.add(obsType.toString()));
        return joiner.toString();
    }

    private static class NullTypesOfObsModel extends TypesOfObsModel {
        @Override
        public String toString() {
            return "NullTypesOfObs";
        }
    }
}

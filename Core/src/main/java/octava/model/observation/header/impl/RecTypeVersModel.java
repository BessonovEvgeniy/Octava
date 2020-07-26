package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RECIEVER_TYPE_VERSIONS")
public @Data class RecTypeVersModel extends BaseModel implements HeaderLabel {

    public static final RecTypeVersModel NULL = new NullRecTypeVersModel();

    private String rec = EMPTY_STRING;
    private String type = EMPTY_STRING;
    private String vers = EMPTY_STRING;

    protected RecTypeVersModel() {}

    public RecTypeVersModel(String rec, String type, String vers) {
        this.rec = rec;
        this.type = type;
        this.vers = vers;
    }

    @Override
    public String toString() {
        return "Rec=" + rec + " Type=" + type + "Vers=" + vers;
    }

    private static class NullRecTypeVersModel extends RecTypeVersModel {
        @Override
        public String toString() {
            return "NullRecTypeVers";
        }
    }

}
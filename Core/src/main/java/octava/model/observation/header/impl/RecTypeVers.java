package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.observation.header.HeaderLabel;

public @Data class RecTypeVers implements HeaderLabel {

    public static final RecTypeVers NULL = new RecTypeVers.NullRecTypeVers();

    private String rec = EMPTY_STRING;
    private String type = EMPTY_STRING;
    private String vers = EMPTY_STRING;

    private RecTypeVers() {}

    public RecTypeVers(String rec, String type, String vers) {
        this.rec = rec;
        this.type = type;
        this.vers = vers;
    }

    @Override
    public String toString() {
        return "Rec=" + rec + " Type=" + type + "Vers=" + vers;
    }

    private static class NullRecTypeVers extends RecTypeVers {
        @Override
        public String toString() {
            return "NullRecTypeVers";
        }
    }

}
package rinex.model.observations.header.impl;

import lombok.Data;
import rinex.model.observations.header.HeaderLabel;

public @Data class RecTypeVers implements HeaderLabel {

    private String rec;

    private String type;

    private String vers;

    private RecTypeVers() {}

    public RecTypeVers(String rec, String type, String vers) {
        this.rec = rec;
        this.type = type;
        this.vers = vers;
    }

    public static final RecTypeVers NULL = new RecTypeVers.NullRecTypeVers();

    private static class NullRecTypeVers extends RecTypeVers {
        @Override
        public String toString() {
            return "NullRecTypeVers";
        }
    }

}
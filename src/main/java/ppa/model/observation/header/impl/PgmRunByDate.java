package ppa.model.observation.header.impl;

import lombok.Data;
import ppa.model.observation.header.HeaderLabel;

import java.time.LocalDateTime;

public @Data class PgmRunByDate implements HeaderLabel {

    private String program;

    private String agency;

    private LocalDateTime created;

    private PgmRunByDate() {}

    public PgmRunByDate(String program, String agency, LocalDateTime created) {
        this.program = program;
        this.agency = agency;
        this.created = created;
    }

    public static final PgmRunByDate NULL = new PgmRunByDate.NullPgmRunByDate();

    private static class NullPgmRunByDate extends PgmRunByDate {
        @Override
        public String toString() {
            return "NullPgmRunByDate";
        }
    }
}

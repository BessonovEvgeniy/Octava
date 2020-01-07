package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.observation.header.HeaderLabel;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public @Data class PgmRunByDate implements HeaderLabel {

    public static final PgmRunByDate NULL = new PgmRunByDate.NullPgmRunByDate();

    private String program;
    private String agency;
    private LocalDateTime created;

    private PgmRunByDate() {}

    public PgmRunByDate(String program, String agency, LocalDateTime created) {
        this.program = program;
        this.agency = agency;
        this.created = created;
    }

    private static class NullPgmRunByDate extends PgmRunByDate {
        @Override
        public String toString() {
            return "NullPgmRunByDate";
        }
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        return joiner.add("Program").add(program).add("agency").add(agency).add("created").add(created.toString()).toString();
    }

}

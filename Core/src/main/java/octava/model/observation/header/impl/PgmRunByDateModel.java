package octava.model.observation.header.impl;

import lombok.Data;
import octava.model.BaseModel;
import octava.model.observation.header.HeaderLabel;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity
@Table(name = "PGM_RUN_BY_DATES")
public @Data class PgmRunByDateModel extends BaseModel implements HeaderLabel {

    @Transient
    public static final PgmRunByDateModel NULL = new NullPgmRunByDateModel();

    private String program;
    private String agency;
    private LocalDateTime created;

    protected PgmRunByDateModel() {}

    public PgmRunByDateModel(final String program, final String agency, final LocalDateTime created) {
        this.program = program;
        this.agency = agency;
        this.created = created;
    }

    private static class NullPgmRunByDateModel extends PgmRunByDateModel {
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

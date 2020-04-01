package octava.model.rinex;

import lombok.Data;
import octava.model.media.MediaModel;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "RINEXFILES")
@Inheritance(strategy = InheritanceType.JOINED)
public @Data class RinexFileMediaModel extends MediaModel {

    private Status status = Status.New;

    public RinexFileMediaModel(){}

    public enum Status {
        New, Ready, Processing, Procced
    }
}

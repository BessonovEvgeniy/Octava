package model.rinex;

import lombok.Data;
import model.BaseModel;
import model.StoredFileModel;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "RINEXFILES")
public @Data class RinexFileModel extends BaseModel {

    private String name;

    private Status status = Status.New;

    @OneToOne
    @PrimaryKeyJoinColumn
    private StoredFileModel storedFile;

    public RinexFileModel(){}

    public RinexFileModel(StoredFileModel storedFile) {
        this.storedFile = storedFile;
    }


    public enum Status {
        New, Ready, Processing, Procced
    }
}

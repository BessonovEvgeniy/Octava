package business.model.receiver;

import lombok.Data;
import business.model.BaseModel;

import javax.persistence.Entity;

@Entity
public @Data class Receiver extends BaseModel {

    private String serialNumber;

    private String user;

    private String company;
}

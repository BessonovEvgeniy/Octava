package octava.model.receiver;

import lombok.Data;
import octava.model.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RECEIVER")
public @Data class Receiver extends BaseModel {

    private String serialNumber;
//
//    private String user;
//
//    private String company;
}

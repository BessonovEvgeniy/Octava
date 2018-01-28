package rinex.model.receiver;

import lombok.Data;
import rinex.model.BaseModel;

import javax.persistence.Entity;

@Entity
public @Data class Receiver extends BaseModel {

    private String serialNumber;

    private String user;

    private String company;
}

package business.model.contact;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public @Data class Contact {

    @Embedded
    private Address address;


}

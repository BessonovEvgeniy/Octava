package rinex.model.observations.header;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
public @Data class RinexHeaderException extends Exception {

    String message;

    public RinexHeaderException(String msg) {
        message = msg;
    }
}

package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public @Data class RinexHeaderException extends Exception {

    String message;

    RinexHeaderException(String msg) {
        message = msg;
    }
}

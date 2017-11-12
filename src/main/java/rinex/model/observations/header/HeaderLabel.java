package rinex.model.observations.header;

import org.springframework.stereotype.Component;

@FunctionalInterface
@Component
public interface HeaderLabel {
    Boolean parse(String line) throws RinexHeaderException;
}

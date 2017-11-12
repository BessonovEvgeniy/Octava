package rinex.model.observations.header;

import org.springframework.stereotype.Service;

@FunctionalInterface
@Service
public interface HeaderLabel {

    Boolean parse(String line) throws RinexHeaderException;
}

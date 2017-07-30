package rinex.service;

import org.springframework.context.annotation.Scope;
import rinex.service.Impl.observations.rinex.rinexImpl.header.RinexHeaderException;

@FunctionalInterface
@Scope("prototype")
public interface HeaderLabel {

    boolean parse(String line) throws RinexHeaderException;
}

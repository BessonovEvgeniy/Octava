package rinex.service.impl.observations.header;

import rinex.model.observations.header.HeaderLabel;

public interface HeaderLabelParserService<T extends HeaderLabel> {

    T parse(String line);
}

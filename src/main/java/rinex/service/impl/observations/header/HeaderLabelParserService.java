package rinex.service.impl.observations.header;

import rinex.model.observations.header.HeaderLabel;

public interface HeaderLabelParserService <T extends HeaderLabel>  {

    int RINEX_LINE_LENGTH = 80;

    T parse(String line);
}

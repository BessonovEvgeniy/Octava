package rinex.service.impl.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rinex.exception.UnknownHeaderLabelException;
import rinex.model.observations.header.HeaderLabel;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.Map;

@Service
public class HeaderLabelFactory {

    @Autowired
    private Map<String, HeaderLabelParserService> parsers;

    public HeaderLabel getHeaderLabel(String line) throws Exception {

        String label = line.substring(60,line.length()).toUpperCase().trim();
        HeaderLabelParserService parser = parsers.get(label);
        if (parser == null) {
            throw new UnknownHeaderLabelException("Unknown header label: " + label);
        }
        return parser.parse(line);
    }
}
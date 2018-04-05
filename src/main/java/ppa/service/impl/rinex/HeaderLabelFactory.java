package ppa.service.impl.rinex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppa.exception.UnknownHeaderLabelException;
import ppa.model.observation.header.HeaderLabel;
import ppa.service.HeaderLabelParserService;

import java.util.Map;

@Service
public class HeaderLabelFactory {

    private Map<String, HeaderLabelParserService> parsers;

    @Autowired
    public HeaderLabelFactory(Map<String, HeaderLabelParserService> parsers) {
        this.parsers = parsers;
    }

    public HeaderLabel getHeaderLabel(String line) throws Exception {

        String label = line.substring(60,line.length()).toUpperCase().trim();

        if (label.contains("COMMENT")) {
            return null;
        }

        HeaderLabelParserService parser = parsers.get(label);
        if (parser == null) {
            throw new UnknownHeaderLabelException("Unknown header label: " + label);
        }
        return parser.parse(line);
    }
}
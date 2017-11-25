package rinex.service.impl.observations.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rinex.exception.UnknownHeaderLabelException;
import rinex.model.observations.header.HeaderLabel;

import java.util.Map;

@Service
public class HeaderLabelFactory {

    @Autowired
    private Map<String, HeaderLabel> headerLabels;

    public HeaderLabel getHeaderLabel(String line) throws Exception {

        String label = line.substring(60,line.length()).toUpperCase().trim();
        HeaderLabel headerLabel = headerLabels.get(label);
        if (headerLabel == null) {
            throw new UnknownHeaderLabelException("Unknown header label: " + label);
        }
        headerLabel.parse(line);
        return headerLabel;
    }
}
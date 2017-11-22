package rinex.service.impl.observations.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rinex.model.observations.header.RinexHeaderException;
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
            throw new RinexHeaderException("Unknown header label: " + label);
        } else {
            headerLabel.parse(line);
        }
        return headerLabel;
    }
}
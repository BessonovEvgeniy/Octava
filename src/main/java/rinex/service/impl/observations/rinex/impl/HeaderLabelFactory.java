package rinex.service.impl.observations.rinex.impl;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rinex.model.observations.header.RinexHeaderException;
import rinex.model.observations.header.HeaderLabel;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Service
public class HeaderLabelFactory {

    @Autowired
    private Map<String, HeaderLabel> headerLabels;

    @NotNull
    @Length(min = 80, max = 80)
    public HeaderLabel getHeaderLabel(String line) throws RinexHeaderException {

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
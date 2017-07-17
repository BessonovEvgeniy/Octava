package rinex.service.Impl.observations.rinex.rinexImpl.header;

import rinex.service.HeaderLabel;

import java.util.HashMap;
import java.util.Map;

public class HeaderLabelFactory {

    private Map<String, HeaderLabel> headerLabels;

    public HeaderLabelFactory() {
        init();
    }

    private void init() {
        headerLabels = new HashMap<>();
        headerLabels.put("RINEX VERSION / TYPE",new RinexVersionType());
        headerLabels.put("PGM / RUN BY / DATE", new PgmRunByDate());
        headerLabels.put("MARKER NAME",         new MarkerName());
        headerLabels.put("OBSERVER / AGENCY",   new ObserverAgency());
    }

    public HeaderLabel getHeaderLabel(String line) throws UnknownHeaderException{

        HeaderLabel headerLabel = headerLabels.get(line.substring(line.length()-20,line.length()).trim());

        if (headerLabel == null) {
            throw new UnknownHeaderException();
        } else {
            headerLabel.parse(line);
        }
        return headerLabel;
    }
}
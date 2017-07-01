package service.Impl.observations.rinex.rinexImpl.header;

import service.HeaderLabel;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class HeaderLabelFactory {
    private HashMap<String, HeaderLabel> headerLabels = new LinkedHashMap<>();

    public HeaderLabelFactory(){
        headerLabels.put("RINEX VERSION / TYPE",new RinexVersionType());
        headerLabels.put("PGM / RUN BY / DATE", new PgmRunByDate());
        headerLabels.put("MARKER NAME",         new MarkerName());
        headerLabels.put("OBSERVER / AGENCY",   new ObserverAgency());
    }

    public HeaderLabel getHeaderLabel(String title) {
        return headerLabels.get(title.substring(title.length()-20,title.length()).trim());
    }
}
package service.observations.rinex;

import service.observations.rinex.headerLabels.*;

import java.util.HashMap;
import java.util.Map;

public class HeaderLabelFactory {
    private HashMap<String, HeaderLabel> headerLabels = new HashMap<>();

    HeaderLabelFactory(){
        headerLabels.put("RINEX VERSION / TYPE",   new RinexVersionType());
        headerLabels.put("PGM / RUN BY / DATE",   new PgmRunByDate());
        headerLabels.put("MARKER NAME",   new MarkerName());
        headerLabels.put("OBSERVER / AGENCY", new ObserverAgency());
    }

    public HeaderLabel getHeaderLabel(String title) {
        for (Map.Entry<String, HeaderLabel> item: headerLabels.entrySet()){
            String titleItem = item.getKey();
            if (title.contains(titleItem)){
                HeaderLabel label = headerLabels.get(titleItem);
                label.parse(title);
                return label;
            }
        }
        return null;
    }
}
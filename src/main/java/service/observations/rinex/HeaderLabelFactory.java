package service.observations.rinex;

import service.observations.rinex.headerLabels.HeaderLabel;
import service.observations.rinex.headerLabels.MarkerName;
import service.observations.rinex.headerLabels.PgmRunByDate;
import service.observations.rinex.headerLabels.RinexVersionType;

import java.util.HashMap;
import java.util.Map;

public class HeaderLabelFactory {
    private HashMap<String, HeaderLabel> headerLabels = new HashMap<>();

    HeaderLabelFactory(){
        headerLabels.put("RINEX VERSION / TYPE",   new RinexVersionType());
        headerLabels.put("PGM / RUN BY / DATE",   new PgmRunByDate());
        headerLabels.put("MARKER NAME",   new MarkerName());
    }

    public HeaderLabel getHeaderLabel(String title) {
        for (Map.Entry<String, HeaderLabel> item: headerLabels.entrySet()){
            String titleItem = item.getKey();
            if (title.contains(titleItem)){
                return headerLabels.get(titleItem);
            }
        }
        return null;
    }
}
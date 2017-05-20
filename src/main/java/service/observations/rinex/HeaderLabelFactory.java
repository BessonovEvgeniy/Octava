package service.observations.rinex;

import service.observations.rinex.headerLabels.HeaderLabel;
import service.observations.rinex.headerLabels.RinexVersionType;

import java.util.HashMap;
import java.util.Map;

public class HeaderLabelFactory {
    HashMap<String, HeaderLabel> headerLabels = new HashMap<>();

    HeaderLabelFactory(){
        headerLabels.put("RINEX VERSION / TYPE",   new RinexVersionType());
    }

    HeaderLabel getHeaderLabel(String title) {
        for (Map.Entry<String, HeaderLabel> item: headerLabels.entrySet()){
            String titleItem = item.getKey();
            if (title.contains(titleItem)){
                return headerLabels.get(titleItem);
            }
        }
        return null;
    }
}
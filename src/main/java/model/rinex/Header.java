package model.rinex;


import service.observations.rinex.headerLabels.HeaderLabel;
import service.observations.rinex.headerLabels.PgmRunByDate;
import service.observations.rinex.headerLabels.RinexVersionType;

import java.util.ArrayList;
import java.util.List;

public class Header  {
    HeaderLabel rinexVersionType = new RinexVersionType();
    HeaderLabel pgmRunByDate = new PgmRunByDate();

    private List<HeaderLabel> headerLabels = new ArrayList<>();

    public void add(HeaderLabel headerLabel){
        headerLabels.add(headerLabel);
    }

}
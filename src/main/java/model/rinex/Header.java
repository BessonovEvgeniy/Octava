package model.rinex;


import service.observations.rinex.headerLabels.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Header  {
    HeaderLabel rinexVersionType = new RinexVersionType();
    HeaderLabel pgmRunByDate = new PgmRunByDate();
    HeaderLabel markerName = new MarkerName();
    HeaderLabel observerAgency = new ObserverAgency();

    private List<HeaderLabel> headerLabels = new ArrayList<>();

    public void set(HeaderLabel headerLabel){
        if (headerLabel instanceof RinexVersionType) {
            rinexVersionType = headerLabel;
        }
        else if (headerLabel instanceof PgmRunByDate) {
            pgmRunByDate = headerLabel;
        }
        else if (headerLabel instanceof MarkerName) {
            markerName = headerLabel;
        }
        else if (headerLabel instanceof ObserverAgency) {
            observerAgency = headerLabel;
        }
    }

}
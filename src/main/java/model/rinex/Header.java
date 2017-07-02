package model.rinex;


import service.observations.rinex.headerLabels.*;

import java.util.ArrayList;
import java.util.List;

public class Header implements RinexData {

    private HeaderLabel rinexVersionType = new RinexVersionType();
    private HeaderLabel pgmRunByDate = new PgmRunByDate();
    private HeaderLabel markerName = new MarkerName();
    private HeaderLabel observerAgency = new ObserverAgency();

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

    public static class HeaderNull extends Header {
        public String toString() {
            return "Null Header";
        }
    }

}
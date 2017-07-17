package rinex.model.rinex;

import lombok.Data;
import rinex.service.HeaderLabel;
import rinex.service.Impl.observations.rinex.rinexImpl.header.MarkerName;
import rinex.service.Impl.observations.rinex.rinexImpl.header.ObserverAgency;
import rinex.service.Impl.observations.rinex.rinexImpl.header.PgmRunByDate;
import rinex.service.Impl.observations.rinex.rinexImpl.header.RinexVersionType;

import java.util.ArrayList;
import java.util.List;

public @Data class Header  {
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
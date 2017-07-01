package service.observations.rinex.header;

import org.junit.Test;
import service.observations.rinex.rinexImpl.header.ObserverAgency;

import static org.junit.Assert.assertEquals;

public class ObserverAgencyTest {

    @Test
    public void testObserverAgency(){
        String observerMessage = "Wrong observer name for \"OBSERVER / AGENCY \" field";
        String agencyMessage = "Wrong marker name of program for \"OBSERVER / AGENCY\" field";

        ObserverAgency observerAgency = new ObserverAgency();

        observerAgency.parse("UNKNOWN1            UNKNOWN2                                OBSERVER / AGENCY");
        assertEquals(observerMessage,"UNKNOWN1", observerAgency.getObserverName());
        assertEquals(agencyMessage,"UNKNOWN2", observerAgency.getAgencyName());

        observerAgency = new ObserverAgency();

        observerAgency.parse("BILL SMITH          ABC INSTITUTE                           OBSERVER / AGENCY");
        assertEquals(observerMessage,"BILL SMITH", observerAgency.getObserverName());
        assertEquals(agencyMessage,"ABC INSTITUTE", observerAgency.getAgencyName());
    }
}
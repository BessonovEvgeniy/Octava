package service.Impl.observations.rinex.header;

import org.junit.Test;
import service.Impl.observations.rinex.rinexImpl.header.MarkerName;

import static org.junit.Assert.assertEquals;

public class MarkerNameTest {

    @Test
    public void testMarkerName(){
        String pgmMessage = "Wrong marker name of program for \"MARKER NAME\" field";

        MarkerName markerName = new MarkerName();

        markerName.parse("X7                                                          MARKER NAME");
        assertEquals(pgmMessage,"X7", markerName.getMarkerName());

        markerName = new MarkerName();

        markerName.parse("A 9080                                                      MARKER NAME");
        assertEquals(pgmMessage,"A 9080", markerName.getMarkerName());
    }
}
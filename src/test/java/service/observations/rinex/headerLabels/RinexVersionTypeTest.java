package service.observations.rinex.headerLabels;

import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class RinexVersionTypeTest {

    @Test
    public void testReadRinexVersion(){
        RinexVersionType rinexVersionType = new RinexVersionType();

        String versionMessage = "Wrong RINEX version";
        String modeMessage = "Wrong RINEX mode";

        rinexVersionType.parse("     2.11           OBSERVATION DATA    M (MIXED)           ");
        assertEquals(versionMessage,"2.11", rinexVersionType.getVersion());
        assertEquals(modeMessage,"M", rinexVersionType.getMode());

        rinexVersionType = new RinexVersionType();

        rinexVersionType.parse("     1.001          OBSERVATION DATA    E (ERROR)           ");
        assertEquals(versionMessage,"1.00", rinexVersionType.getVersion());
        assertNull(modeMessage, rinexVersionType.getMode());
    }
}
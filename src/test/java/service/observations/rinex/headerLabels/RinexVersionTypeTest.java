package service.observations.rinex.headerLabels;

import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class RinexVersionTypeTest {

    @Test
    public void testReadRinexVersion(){
        String versionMessage = "Wrong RINEX version";
        String modeMessage = "Wrong RINEX mode";

        RinexVersionType rinexVersionType = new RinexVersionType();

        rinexVersionType.parse("     2.11           OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE");
        assertEquals(versionMessage,"2.11", rinexVersionType.getVersion());
        assertEquals(modeMessage,"M", rinexVersionType.getMode());

        rinexVersionType = new RinexVersionType();

        rinexVersionType.parse("     1.001          OBSERVATION DATA    E (ERROR)           RINEX VERSION / TYPE");
        assertEquals(versionMessage,"1.00", rinexVersionType.getVersion());
        assertEquals(modeMessage,"", rinexVersionType.getMode());
    }
}
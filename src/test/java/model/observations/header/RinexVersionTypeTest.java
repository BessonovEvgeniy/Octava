package model.observations.header;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import rinex.config.AppInitializer;
import rinex.config.MvcConfiguration;
import rinex.model.observations.header.RinexVersionType;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class RinexVersionTypeTest {

    private final String versionMessage = "Wrong RINEX version";
    private final String modeMessage = "Wrong RINEX mode";

    @Autowired
    RinexVersionType rinexVersionType;

    @Test
    public void testReadValidRinexObsType() {
        Assert.assertTrue(rinexVersionType.parse("     2.11           OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "M", rinexVersionType.getMode());

        Assert.assertTrue(rinexVersionType.parse("     2.11           OBSERVATION DATA    E (GALILEO)         RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "E", rinexVersionType.getMode());

        Assert.assertTrue(rinexVersionType.parse("     2.11           OBSERVATION DATA    R (GLONASS)         RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "R", rinexVersionType.getMode());

        Assert.assertTrue(rinexVersionType.parse("     2.11           OBSERVATION DATA    G (GPS)             RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "G", rinexVersionType.getMode());

        Assert.assertTrue(rinexVersionType.parse("     2.11           OBSERVATION DATA    S (GEO)             RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "S", rinexVersionType.getMode());
    }

    @Test
    public void testReadValidRinexVersionType() {
        Assert.assertTrue(rinexVersionType.parse("     2.11           OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "2.11", rinexVersionType.getVersion());

        Assert.assertTrue(rinexVersionType.parse("     45.11          OBSERVATION DATA    E (GALILEO)         RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "45.11", rinexVersionType.getVersion());

        Assert.assertTrue(rinexVersionType.parse("     345.11         OBSERVATION DATA    R (GLONASS)         RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "345.11", rinexVersionType.getVersion());

        Assert.assertTrue(rinexVersionType.parse("     2345.11        OBSERVATION DATA    G (GPS)             RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "2345.11", rinexVersionType.getVersion());

        Assert.assertTrue(rinexVersionType.parse("     12345.11       OBSERVATION DATA    S (GEO)             RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "12345.11", rinexVersionType.getMode());

        Assert.assertTrue(rinexVersionType.parse("     123456.11      OBSERVATION DATA    S (GEO)             RINEX VERSION / TYPE"));
        assertEquals(modeMessage, "12345.11", rinexVersionType.getMode());
    }

//    @Test(expected = UnknownRinexHeaderException.class)
//    public void rinexHeaderExceptionTest() throws UnknownRinexHeaderException {
//        RinexVersionType rinexVersionType = new RinexVersionType();
//        rinexVersionType.parse("     1.001          OBSERVATION DATA    E (ERROR)           RINEX VERSION / TYPE");
//    }
}
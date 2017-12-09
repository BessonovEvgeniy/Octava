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
import rinex.exception.InvalidHeaderLabelException;
import rinex.exception.RinexLineLengthMismatchException;
import rinex.model.observations.header.RinexVersionType;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class RinexVersionTypeTest {

    private final String versionMessage = "Wrong RINEX version";
    private final String modeMessage = "Wrong RINEX mode";

    @Autowired
    private RinexVersionType rinexVersionType;

    @Test
    public void testParseValidRinexObsMode() {
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
    public void testParseValidRinexVersionType() {
        Assert.assertTrue(rinexVersionType.parse("     2.11           OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE"));
        assertEquals(versionMessage, "2.11", rinexVersionType.getVersion());

        Assert.assertTrue(rinexVersionType.parse("     45.11          OBSERVATION DATA    E (GALILEO)         RINEX VERSION / TYPE"));
        assertEquals(versionMessage, "45.11", rinexVersionType.getVersion());

        Assert.assertTrue(rinexVersionType.parse("     12345678.12    OBSERVATION DATA    S (GEO)             RINEX VERSION / TYPE"));
        assertEquals(versionMessage, "12345678.12", rinexVersionType.getVersion());
    }

    @Test
    public void testHaveToThrowInvalidHeaderLabelException() {

        Throwable exception = assertThrows(InvalidHeaderLabelException.class, () ->
                rinexVersionType.parse("     2.11           OBSERVATION DATA    F (MIXED)           RINEX VERSION / TYPE")
        );
        Assert.assertEquals(exception.getMessage(), "RinexVersionType");

        exception = assertThrows(InvalidHeaderLabelException.class, () ->
                rinexVersionType.parse("     2.111          OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE")
        );
        Assert.assertEquals(exception.getMessage(), "RinexVersionType");

        exception = assertThrows(InvalidHeaderLabelException.class, () ->
                rinexVersionType.parse("   12345678.11      OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE")
        );
        Assert.assertEquals(exception.getMessage(), "RinexVersionType");

        exception = assertThrows(InvalidHeaderLabelException.class, () ->
                rinexVersionType.parse("    123456789.11    OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE")
        );
        Assert.assertEquals(exception.getMessage(), "RinexVersionType");
    }

    @Test
    public void testLineLengthMismatch() {

        RinexLineLengthMismatchException exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                rinexVersionType.parse("    123456789.11    OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE ")
        );
        Assert.assertTrue(exception.getMessage() != null);


        exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                rinexVersionType.parse("    123456789.11    OBSERVATION DATA    M (MIXED)          RINEX VERSION / TYPE")
        );
        Assert.assertTrue(exception.getMessage() != null);
    }
}
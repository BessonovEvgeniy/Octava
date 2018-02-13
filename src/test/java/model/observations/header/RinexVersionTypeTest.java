package model.observations.header;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import config.AppInitializer;
import config.MvcConfiguration;
import ppa.exception.RinexLineLengthMismatchException;
import ppa.model.observation.header.impl.RinexVersionType;
import ppa.service.impl.observations.header.impl.RinexVersionTypeParserServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class RinexVersionTypeTest {

    private final String versionMessage = "Wrong RINEX version";
    private final String modeMessage = "Wrong RINEX mode";

    @Autowired
    private RinexVersionTypeParserServiceImpl parser;

    @Test
    public void testParseValidRinexObsMode() {
        RinexVersionType rinexVersionType;

        rinexVersionType = parser.parse("     2.11           OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE");
        assertEquals(modeMessage, "M", rinexVersionType.getMode());

        rinexVersionType = parser.parse("     2.11           OBSERVATION DATA    E (GALILEO)         RINEX VERSION / TYPE");
        assertEquals(modeMessage, "E", rinexVersionType.getMode());

        rinexVersionType = parser.parse("     2.11           OBSERVATION DATA    R (GLONASS)         RINEX VERSION / TYPE");
        assertEquals(modeMessage, "R", rinexVersionType.getMode());

        rinexVersionType = parser.parse("     2.11           OBSERVATION DATA    G (GPS)             RINEX VERSION / TYPE");
        assertEquals(modeMessage, "G", rinexVersionType.getMode());

        rinexVersionType = parser.parse("     2.11           OBSERVATION DATA    S (GEO)             RINEX VERSION / TYPE");
        assertEquals(modeMessage, "S", rinexVersionType.getMode());
    }

    @Test
    public void testParseValidRinexVersionType() {
        RinexVersionType rinexVersionType;

        rinexVersionType = parser.parse("     2.11           OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE");
        assertEquals(versionMessage, "2.11", rinexVersionType.getVersion());

        rinexVersionType = parser.parse("     45.11          OBSERVATION DATA    E (GALILEO)         RINEX VERSION / TYPE");
        assertEquals(versionMessage, "45.11", rinexVersionType.getVersion());

        rinexVersionType = parser.parse("     12345678.12    OBSERVATION DATA    S (GEO)             RINEX VERSION / TYPE");
        assertEquals(versionMessage, "12345678.12", rinexVersionType.getVersion());
    }

    @Test
    public void testHaveToReceiveRinexVersionTypeNullObject() {
        RinexVersionType rinexVersionType;

        rinexVersionType = parser.parse("     2.11           OBSERVATION DATA    F (MIXED)           RINEX VERSION / TYPE");
        Assert.assertTrue(rinexVersionType.equals(RinexVersionType.NULL));

        rinexVersionType = parser.parse("     2.111          OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE");
        Assert.assertTrue(rinexVersionType.equals(RinexVersionType.NULL));

        rinexVersionType = parser.parse("   12345678.11      OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE");
        Assert.assertTrue(rinexVersionType.equals(RinexVersionType.NULL));

        rinexVersionType = parser.parse("    123456789.11    OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE");
        Assert.assertTrue(rinexVersionType.equals(RinexVersionType.NULL));
    }

    @Test
    public void testLineLengthMismatch() {

        RinexLineLengthMismatchException exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                parser.parse("    123456789.11    OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE ")
        );
        Assert.assertTrue(exception.getMessage() != null);


        exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                parser.parse("    123456789.11    OBSERVATION DATA    M (MIXED)          RINEX VERSION / TYPE")
        );
        Assert.assertTrue(exception.getMessage() != null);
    }
}
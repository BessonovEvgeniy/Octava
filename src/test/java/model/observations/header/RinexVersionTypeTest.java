package model.observations.header;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rinex.config.AppInitializer;
import rinex.config.MvcConfiguration;
import rinex.model.observations.header.RinexHeaderException;
import rinex.model.observations.header.RinexVersionType;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
public class RinexVersionTypeTest {
//
    @Autowired
    RinexVersionType rinexVersionType;

    @Test
    public void testReadRinexVersion() throws RinexHeaderException {
        String versionMessage = "Wrong RINEX version";
        String modeMessage = "Wrong RINEX mode";
/*

        Assert.assertTrue(rinexVersionType.parse("     2.11           OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE"));
        assertEquals(versionMessage, "2.11", rinexVersionType.getVersion());
        assertEquals(modeMessage, "M", rinexVersionType.getMode());

        Assert.assertTrue(rinexVersionType.parse("     1.00          OBSERVATION DATA    E (ERROR)           RINEX VERSION / TYPE"));
        assertEquals(versionMessage, "1.00", rinexVersionType.getVersion());
        assertEquals(modeMessage, "E", rinexVersionType.getMode());

        rinexVersionType.setVersion("2.11");
        assertEquals(versionMessage, "2.11", rinexVersionType.getVersion());

        rinexVersionType.setMode("M");
        assertEquals(modeMessage, "M", rinexVersionType.getMode());
*/

//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        Validator validator = validatorFactory.getValidator();
//
//        rinexVersionType.setVersion("2.1122");
//        validate = validator.validate(rinexVersionType);
//        assertEquals(modeMessage,1, validate.size());
//
//        rinexVersionType.setMode("23");
//        validate = validator.validate(rinexVersionType);
//        assertEquals(modeMessage,2, validate.size());
    }

//    @Test(expected = RinexHeaderException.class)
//    public void rinexHeaderExceptionTest() throws RinexHeaderException {
//        RinexVersionType rinexVersionType = new RinexVersionType();
//        rinexVersionType.parse("     1.001          OBSERVATION DATA    E (ERROR)           RINEX VERSION / TYPE");
//    }
}
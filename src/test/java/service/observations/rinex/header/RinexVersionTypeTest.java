package service.observations.rinex.header;

import org.junit.Assert;
import org.junit.Test;
import service.observations.rinex.rinexImpl.header.RinexVersionType;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class RinexVersionTypeTest {

    @Test
    public void testReadRinexVersion(){
        String versionMessage = "Wrong RINEX version";
        String modeMessage = "Wrong RINEX mode";

        RinexVersionType rinexVersionType = new RinexVersionType();
        Set<ConstraintViolation<RinexVersionType>> validate = new HashSet<ConstraintViolation<RinexVersionType>>();

        Assert.assertTrue(rinexVersionType.parse("     2.11           OBSERVATION DATA    M (MIXED)           RINEX VERSION / TYPE"));
        assertEquals(versionMessage,"2.11", rinexVersionType.getVersion());
        assertEquals(modeMessage,"M", rinexVersionType.getMode());

        Assert.assertFalse(rinexVersionType.parse("     1.001          OBSERVATION DATA    E (ERROR)           RINEX VERSION / TYPE"));
        assertNull(versionMessage, rinexVersionType.getVersion());
        assertNull(modeMessage, rinexVersionType.getMode());

        Assert.assertTrue(rinexVersionType.parse("     1.00          OBSERVATION DATA    E (ERROR)           RINEX VERSION / TYPE"));
        assertEquals(versionMessage,"1.00", rinexVersionType.getVersion());
        assertEquals(modeMessage,"E", rinexVersionType.getMode());

        rinexVersionType.setVersion("2.11");
        assertEquals(versionMessage,"2.11", rinexVersionType.getVersion());

        rinexVersionType.setMode("M");
        assertEquals(modeMessage,"M", rinexVersionType.getMode());

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        rinexVersionType.setVersion("2.1122");
        validate = validator.validate(rinexVersionType);
        assertEquals(modeMessage,1, validate.size());

        rinexVersionType.setMode("23");
        validate = validator.validate(rinexVersionType);
        assertEquals(modeMessage,2, validate.size());
    }
}
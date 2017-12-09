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
import rinex.exception.RinexLineLengthMismatchException;
import rinex.model.observations.header.ObserverAgency;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class ObserverAgencyTest {

    @Autowired
    private ObserverAgency observerAgency;

    @Test
    public void testMaxLengthViolation() {
        Throwable exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                observerAgency.parse("81 chars in this line.                                       OBSERVER / AGENCY   ")
        );

        Assert.assertEquals(exception.getClass(), RinexLineLengthMismatchException.class);
    }

    @Test
    public void testValidObserverAgencyFields() {
        observerAgency.parse("012345678901234567890123456789012345678901234567890123456789OBSERVER / AGENCY   ");

        Assert.assertEquals("0123456789012345678901234567890123456789", observerAgency.getAgencyName());
        Assert.assertEquals("01234567890123456789", observerAgency.getObserverName());
    }
}
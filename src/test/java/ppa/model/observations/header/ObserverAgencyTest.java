package ppa.model.observations.header;

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
import ppa.model.observation.header.impl.ObserverAgency;
import ppa.service.impl.observations.header.impl.ObserverAgencyParserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class ObserverAgencyTest {

    @Autowired
    private ObserverAgencyParserServiceImpl parser;

    @Test
    public void testMaxLengthViolation() {
        Throwable exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                parser.parse("81 chars in this line.                                       OBSERVER / AGENCY   ")
        );

        Assert.assertEquals(exception.getClass(), RinexLineLengthMismatchException.class);
    }

    @Test
    public void testValidObserverAgencyFields() {
        ObserverAgency observerAgency = parser.parse("012345678901234567890123456789012345678901234567890123456789OBSERVER / AGENCY   ");

        Assert.assertEquals("0123456789012345678901234567890123456789", observerAgency.getAgencyName());
        Assert.assertEquals("01234567890123456789", observerAgency.getObserverName());
    }
}
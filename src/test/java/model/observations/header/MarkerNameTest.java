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
import rinex.service.impl.observations.header.impl.MarkerNameParserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class MarkerNameTest {

    @Autowired
    private MarkerNameParserServiceImpl parser;

    @Test
    public void testMaxLengthViolation() {
        Throwable exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                parser.parse("81 chars in this line.                                       MARKER NAME         ")
        );
        Assert.assertEquals(exception.getClass(), RinexLineLengthMismatchException.class);
    }
}
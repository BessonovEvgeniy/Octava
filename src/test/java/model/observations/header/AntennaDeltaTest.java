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
import rinex.model.observations.header.AntennaDelta;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class AntennaDeltaTest {

    private static final double DELTA = 1e-15;

    @Autowired
    private AntennaDelta antennaDelta;

    @Test
    public void testMaxLengthViolation() {
        Throwable exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                antennaDelta.parse("         1.5520        0.0000        0.0000                  ANTENNA: DELTA H/E/N")
        );
        Assert.assertEquals(exception.getClass(), RinexLineLengthMismatchException.class);
    }

    @Test
    public void testValidAntennaDelta() {
        String line = " 12345678.5520 12345678.0000 12345678.0000                  ANTENNA: DELTA H/E/N";

        boolean find = antennaDelta.parse(line);

        Assert.assertTrue(find);
        Assert.assertEquals(12345678.5520d, antennaDelta.getDelH(), DELTA);
        Assert.assertEquals(12345678.0000d, antennaDelta.getDelE(), DELTA);
        Assert.assertEquals(12345678.0000d, antennaDelta.getDelN(), DELTA);

        line = "        1.5520        0.0000        0.0000                  ANTENNA: DELTA H/E/N";

        find = antennaDelta.parse(line);

        Assert.assertTrue(find);
        Assert.assertEquals(1.552d, antennaDelta.getDelH(), DELTA);
        Assert.assertEquals(0.0000d, antennaDelta.getDelE(), DELTA);
        Assert.assertEquals(0.0000d, antennaDelta.getDelN(), DELTA);

        line = "         .5520         .0000         .0000                  ANTENNA: DELTA H/E/N";

        find = antennaDelta.parse(line);

        Assert.assertTrue(find);
        Assert.assertEquals(0.552d, antennaDelta.getDelH(), DELTA);
        Assert.assertEquals(0d, antennaDelta.getDelE(), DELTA);
        Assert.assertEquals(0d, antennaDelta.getDelN(), DELTA);

        line = "         .552          .00           .0                     ANTENNA: DELTA H/E/N";

        find = antennaDelta.parse(line);

        Assert.assertTrue(find);
        Assert.assertEquals(0.552d, antennaDelta.getDelH(), DELTA);
        Assert.assertEquals(0d, antennaDelta.getDelE(), DELTA);
        Assert.assertEquals(0d, antennaDelta.getDelN(), DELTA);
    }
}

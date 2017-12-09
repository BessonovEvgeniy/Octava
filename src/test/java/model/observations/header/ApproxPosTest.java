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
import rinex.model.observations.header.ApproxPos;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class ApproxPosTest {

    private static final double DELTA = 1e-15;

    @Autowired
    private ApproxPos approxPos;

    @Test
    public void testMaxLengthViolation() {
        Throwable exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                approxPos.parse("   3850898.3333  2231889.1912  4553102.5291                  APPROX POSITION XYZ ")
        );

        Assert.assertEquals(exception.getClass(), RinexLineLengthMismatchException.class);
    }

    @Test
    public void testValidApproxPos() {
        String line = "  3850898.3333  2231889.1912  4553102.5291                  APPROX POSITION XYZ ";

        approxPos.parse(line);

        Assert.assertEquals(3850898.3333d, approxPos.getX(), DELTA);
        Assert.assertEquals(2231889.1912d, approxPos.getY(), DELTA);
        Assert.assertEquals(4553102.5291d, approxPos.getZ(), DELTA);

        line = "  3850898.333   2231889.191   4553102.529                   APPROX POSITION XYZ ";
        approxPos.parse(line);

        Assert.assertEquals(3850898.333d, approxPos.getX(), DELTA);
        Assert.assertEquals(2231889.191d, approxPos.getY(), DELTA);
        Assert.assertEquals(4553102.529d, approxPos.getZ(), DELTA);

        line = "  3850898.      2231889.1     4553102.52                    APPROX POSITION XYZ ";
        approxPos.parse(line);

        Assert.assertEquals(3850898.0d, approxPos.getX(), DELTA);
        Assert.assertEquals(2231889.1d, approxPos.getY(), DELTA);
        Assert.assertEquals(4553102.52d, approxPos.getZ(), DELTA);

        line = "  3850898       2231889.      4553102.5                     APPROX POSITION XYZ ";
        approxPos.parse(line);

        Assert.assertEquals(3850898.0d, approxPos.getX(), DELTA);
        Assert.assertEquals(2231889.0d, approxPos.getY(), DELTA);
        Assert.assertEquals(4553102.5d, approxPos.getZ(), DELTA);
    }

    @Test
    public void testInvalidApproxPos() {
        String line = "  3850898.33331 2231889.1912  4553102.5291                  APPROX POSITION XYZ ";
        Assert.assertFalse(approxPos.parse(line));

        line = "  3850898.3333  2231889.19121 4553102.5291                  APPROX POSITION XYZ ";
        Assert.assertFalse(approxPos.parse(line));

        line = "  3850898.3333  2231889.1912  4553102.52911                 APPROX POSITION XYZ ";
        Assert.assertFalse(approxPos.parse(line));
    }
}

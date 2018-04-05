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
import ppa.model.observation.header.impl.PgmRunByDate;
import ppa.service.impl.observations.header.PgmRunByDateParserServiceImpl;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class PgmRunByDateTest {

    private final String dateMessage = "Wrong date for PGM / RUN BY / DATE field";

    @Autowired
    PgmRunByDateParserServiceImpl parser;

    @Test
    public void testMaxLengthViolation() {
        Throwable exception = assertThrows(RinexLineLengthMismatchException.class, () ->
                parser.parse("81 chars in this line.                   20170506 11:52:00UTCPGM / RUN BY / DATE ")
        );
        Assert.assertEquals(exception.getClass(), RinexLineLengthMismatchException.class);
    }

    @Test
    public void testParseValidPgmRunByDate() {
        LocalDateTime create = LocalDateTime.of(2017,5,6, 11, 52, 0);

        PgmRunByDate pgmRunByDate;

        pgmRunByDate = parser.parse("teqc  2013Mar15                         20170506 11:52:00UTCPGM / RUN BY / DATE ");
        assertTrue(create.equals(pgmRunByDate.getCreated()), dateMessage);

        pgmRunByDate = parser.parse("XXRINEXO V9.9       AIUB                06-MAY-17 11:52     PGM / RUN BY / DATE ");
        assertTrue(create.equals(pgmRunByDate.getCreated()), dateMessage);

        create = LocalDateTime.of(1999,5,6, 11, 52, 0);

        pgmRunByDate = parser.parse("teqc  2013Mar15                         19990506 11:52:00UTCPGM / RUN BY / DATE ");
        assertTrue(create.equals(pgmRunByDate.getCreated()), dateMessage);

        create = LocalDateTime.of(2090,5,6, 11, 52, 0);

        pgmRunByDate = parser.parse("XXRINEXO V9.9       AIUB                06-MAY-90 11:52     PGM / RUN BY / DATE ");
        assertTrue(create.equals(pgmRunByDate.getCreated()), dateMessage);
    }

    @Test
    public void testParseInvalidPgmRunByDate() {
        Throwable exception = assertThrows(IllegalStateException.class, () ->
                parser.parse("WRONG YEAR          AIUB                06-MAY-91 11:52     PGM / RUN BY / DATE ")
        );

        Assert.assertEquals(exception.getClass(), IllegalStateException.class);
    }
}
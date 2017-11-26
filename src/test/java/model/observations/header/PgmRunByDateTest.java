package model.observations.header;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import rinex.config.AppInitializer;
import rinex.config.MvcConfiguration;
import rinex.model.observations.header.PgmRunByDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class PgmRunByDateTest {

    private final String pgmMessage = "Wrong name of program for PGM / RUN BY / DATE field";
    private final String runByMessage = "Wrong name of agency for PGM / RUN BY / DATE field";
    private final String dateMessage = "Wrong date for PGM / RUN BY / DATE field";

    @Autowired
    PgmRunByDate pgmRunByDate;

    @Test
    public void testParseValidPgmRunByDate(){

        pgmRunByDate.parse("teqc  2013Mar15                         20170506 11:52:45UTCPGM / RUN BY / DATE");
        assertEquals(pgmMessage,"teqc  2013Mar15", pgmRunByDate.getProgram());
        assertEquals(runByMessage,"",pgmRunByDate.getAgency());
        assertEquals(dateMessage,"20170506 11:52:45UTC", pgmRunByDate.getCreated());

        pgmRunByDate.parse("XXRINEXO V9.9       AIUB                24-MAR-01 14:43     PGM / RUN BY / DATE");
        assertEquals(pgmMessage,"XXRINEXO V9.9", pgmRunByDate.getProgram());
        assertEquals(runByMessage,"AIUB", pgmRunByDate.getAgency());
        assertEquals(dateMessage,"24-MAR-01 14:43", pgmRunByDate.getCreated());
    }
}
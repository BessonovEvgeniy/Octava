package service.Impl.observations.rinex.header;

import org.junit.Test;
import service.Impl.observations.rinex.rinexImpl.header.PgmRunByDate;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

public class PgmRunByDateTest {

    @Test
    public void testPgmRunByDate(){
        String pgmMessage = "Wrong name of program for PGM / RUN BY / DATE field";
        String runByMessage = "Wrong name of agency for PGM / RUN BY / DATE field";
        String dateMessage = "Wrong date for PGM / RUN BY / DATE field";

        PgmRunByDate pgmRunByDate = new PgmRunByDate();

        pgmRunByDate.parse("teqc  2013Mar15                         20170506 11:52:45UTCPGM / RUN BY / DATE");
        assertEquals(pgmMessage,"teqc  2013Mar15", pgmRunByDate.getProgram());
        assertEquals(runByMessage,"",pgmRunByDate.getAgency());
        assertEquals(dateMessage,"20170506 11:52:45UTC", pgmRunByDate.getCreated());

        pgmRunByDate = new PgmRunByDate();

        pgmRunByDate.parse("XXRINEXO V9.9       AIUB                24-MAR-01 14:43     PGM / RUN BY / DATE");
        assertEquals(pgmMessage,"XXRINEXO V9.9", pgmRunByDate.getProgram());
        assertEquals(runByMessage,"AIUB", pgmRunByDate.getAgency());
        assertEquals(dateMessage,"24-MAR-01 14:43", pgmRunByDate.getCreated());
    }
}
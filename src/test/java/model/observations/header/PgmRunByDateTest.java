package model.observations.header;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rinex.config.AppInitializer;
import rinex.config.MvcConfiguration;
import rinex.model.observations.header.PgmRunByDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MvcConfiguration.class, AppInitializer.class})
public class PgmRunByDateTest {

    @Autowired
    PgmRunByDate pgmRunByDate;

    @Test
    public void testPgmRunByDate(){
        String pgmMessage = "Wrong name of program for PGM / RUN BY / DATE field";
        String runByMessage = "Wrong name of agency for PGM / RUN BY / DATE field";
        String dateMessage = "Wrong date for PGM / RUN BY / DATE field";
/*
        pgmRunByDate.parse("teqc  2013Mar15                         20170506 11:52:45UTCPGM / RUN BY / DATE");
        assertEquals(pgmMessage,"teqc  2013Mar15", pgmRunByDate.getProgram());
        assertEquals(runByMessage,"",pgmRunByDate.getAgency());
        assertEquals(dateMessage,"20170506 11:52:45UTC", pgmRunByDate.getCreated());

        pgmRunByDate = new PgmRunByDate();

        pgmRunByDate.parse("XXRINEXO V9.9       AIUB                24-MAR-01 14:43     PGM / RUN BY / DATE");
        assertEquals(pgmMessage,"XXRINEXO V9.9", pgmRunByDate.getProgram());
        assertEquals(runByMessage,"AIUB", pgmRunByDate.getAgency());
        assertEquals(dateMessage,"24-MAR-01 14:43", pgmRunByDate.getCreated());*/
    }
}
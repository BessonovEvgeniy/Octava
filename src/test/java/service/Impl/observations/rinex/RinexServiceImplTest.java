package service.Impl.observations.rinex;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rinex.service.RinexService;

import java.io.File;

public class RinexServiceImplTest {


    RinexService rinexService;

    @Autowired
    public RinexServiceImplTest(RinexService rinexServ) {
        rinexService = rinexServ;
    }

    @Test
    public void testReadEmptyFileName() throws Exception {
        File file = new File("resources/X07100.17o");
    }
}

package service.Impl.observations.rinex;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rinex.service.Impl.observations.rinex.rinexImpl.RinexServiceImpl;

import java.io.File;

public class RinexServiceImplTest {

    @Autowired
    RinexServiceImpl rinexService;

    @Test
    public void testReadEmptyFileName() throws Exception {
        File file = new File("resources/X07100.17o");
    }
}

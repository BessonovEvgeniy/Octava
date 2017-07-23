package service.Impl.observations.rinex;

import org.springframework.beans.factory.annotation.Autowired;
import rinex.service.Impl.observations.rinex.rinexImpl.RinexServiceImpl;

public class RinexServiceImplTest {

    @Autowired
    RinexServiceImpl rinexService;

//    @Test
//    public void testReadEmptyFileName(BufferedReader reader) throws Exception {
//        assertNull("TNP file with empty file name", rinexService.readRinex(reader));
//    }
}

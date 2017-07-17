package service.Impl.observations.rinex;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rinex.service.Impl.observations.rinex.rinexImpl.RinexServiceImpl;

import java.io.BufferedReader;

import static org.junit.Assert.assertNull;

public class RinexServiceImplTest {

    @Autowired
    RinexServiceImpl rinexService;

    @Test
    public void testReadEmptyFileName(BufferedReader reader) throws Exception {
        assertNull("TNP file with empty file name", rinexService.readRinex(reader));
    }
}

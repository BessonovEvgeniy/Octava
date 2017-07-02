package service.observations.rinex;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

import static org.junit.Assert.assertNull;

public class RinexServiceTest {

    @Autowired
    RinexService rinexService;

    @Test
    public void testReadEmptyFileName(InputStream file){
        assertNull("TNP file with empty file name", rinexService.readRinex(file));
    }
}

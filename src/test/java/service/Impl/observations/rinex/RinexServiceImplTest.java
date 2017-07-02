package service.Impl.observations.rinex;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import service.Impl.observations.rinex.rinexImpl.RinexServiceImpl;

import java.io.InputStream;

import static org.junit.Assert.assertNull;

public class RinexServiceImplTest {

    @Autowired
    RinexServiceImpl rinexService;

    @Test
    public void testReadEmptyFileName(InputStream file){
        assertNull("TNP file with empty file name", rinexService.readRinex(file));
    }
}

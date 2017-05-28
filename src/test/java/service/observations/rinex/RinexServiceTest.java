package service.observations.rinex;

import model.observations.TNP;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;

public class RinexServiceTest {

    @Autowired
    RinexService rinexService;

    @Test
    public void testReadEmptyFileName(String fileName){
        assertNull("TNP file with empty file name", rinexService.readRinex(fileName));
    }
}

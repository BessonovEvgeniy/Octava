package service.Impl.rinex;

import org.apache.commons.fileupload.FileUploadException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import rinex.config.AppInitializer;
import rinex.config.MvcConfiguration;
import rinex.service.RinexService;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class RinexServiceImplTest {

    @Autowired
    private RinexService rinexService;

    @Test
    public void validateWrongMultipartFile() throws Exception {

        MockMultipartFile rinexMock = new MockMultipartFile("data", "filename.13g", "text/plain", "some xml".getBytes());

        FileUploadException exception = assertThrows(FileUploadException.class, () ->
                rinexService.validateRinex(rinexMock)
        );
        Assert.assertEquals(exception.getClass(), FileUploadException.class);
    }

    @Test
    public void validateCorrectMultipartFile() throws Exception {

        MockMultipartFile rinexMock = new MockMultipartFile("data", "filename.13o", "text/plain", "some xml".getBytes());
        rinexService.validateRinex(rinexMock);
    }
}

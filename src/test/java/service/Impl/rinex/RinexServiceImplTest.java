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
import config.AppInitializer;
import config.MvcConfiguration;
import ppa.service.RinexService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class RinexServiceImplTest {

    @Autowired
    private RinexService rinexService;

    private ClassLoader classLoader = this.getClass().getClassLoader();

    private List<String> headerFiles = Arrays.asList(
            "HeaderReadingIntegrationTest_1.16o",
            "HeaderReadingIntegrationTest_2.17o"
            );

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
        for (String file : headerFiles) {
            String fullPath = classLoader.getResource(file).getFile().replaceFirst("/", "");

            byte[] data = Files.readAllBytes(Paths.get(fullPath));
            MockMultipartFile rinexMock = new MockMultipartFile("data", "src/main/HeaderReadingIntegrationTest_1.16o", "text/plain", data);
            rinexService.validateRinex(rinexMock);
        }
    }
}

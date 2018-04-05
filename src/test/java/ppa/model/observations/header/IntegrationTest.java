package ppa.model.observations.header;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import config.AppInitializer;
import config.MvcConfiguration;
import ppa.service.impl.rinex.ReadHeaderImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class})
@WebAppConfiguration
public class IntegrationTest {

    @Autowired
    private ReadHeaderImpl headerReader;

    @Test
    public void testMaxLengthViolation() {

    }
}

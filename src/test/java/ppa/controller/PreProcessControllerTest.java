package ppa.controller;

import business.model.process.Process;
import config.AppInitializer;
import config.HibernateConfiguration;
import config.MvcConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ppa.model.observation.ReceiverDataModel;
import ppa.service.RinexService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class, MvcConfiguration.class, HibernateConfiguration.class})
@WebAppConfiguration
public class PreProcessControllerTest {

    @Autowired
    private RinexService service;

    @Test
    public void preProcessIntegrationTest() {

        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(classLoader.getResource("38541890.16o").getFile());
        Assert.assertTrue(file.exists());

        List<ReceiverDataModel> processedRinexFiles = new ArrayList<>();

        try {
            Process process = mock(Process.class);
            when(process.getFiles()).thenReturn(Arrays.asList(file));

            processedRinexFiles = service.readRinex(process);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Finished");

    }
}

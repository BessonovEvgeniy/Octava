package octava.facade.impl;

import octava.controller.PPAController;
import octava.facade.PPAFacade;
import octava.model.observation.ReceiverDataModel;
import octava.service.RinexReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component
public class PPAFacadeImpl implements PPAFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PPAController.class);

    @Resource(name = "graphicalPPARinexReaderService")
    private RinexReaderService rinexReaderService;

    @Async
    @Override
    public void process(final File file) {

        LOG.info("Processing " + file.getName() + " file...");

        try {

            final ReceiverDataModel receiverDataModel = rinexReaderService.read(file);

            //ProjectDto WAVELENGTH FACT L1/2

            //Determine Data Rate

            //Shift whole data to Integer seconds

            //Ephemeris processing

            //First positioning approach

            //PlotCAtest

        } catch (Exception e) {
//                LOG.warn("File " + file.getAbsolutePath() + " isn't processed due to some problems. File skipped.");
            e.printStackTrace();
        }
    }
}

package octava.facade.impl;

import octava.controller.PPAController;
import octava.facade.PPAFacade;
import octava.model.observation.ReceiverDataModel;
import octava.model.observation.header.impl.ObsType;
import octava.service.RinexReader;
import octava.service.impl.observations.processors.impl.EnhancedSectionService;
import octava.util.plot.Figure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;

@Component
public class PPAFacadeImpl implements PPAFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PPAController.class);

//    @Resource
//    private RinexFileService rinexFileService;

    @Resource
    private RinexReader rinexReader;

    @Resource
    private EnhancedSectionService enhancedSectionService;

    @PostConstruct
    private void init() {

    }

    @Async
    @Override
    public void process(final File file) {

        LOG.info("Processing " + file.getName() + " file...");
        try {
            LOG.info("Reading RINEX data...");

            final ReceiverDataModel rdm = rinexReader.read(file);

            enhancedSectionService.find(rdm);

            final Figure fig = new Figure("Observations", "Time, sec", "Pseudo distance");
            fig.plot(rdm, ObsType.C1);
            fig.display();

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

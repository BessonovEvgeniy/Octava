package octava.facade;

import octava.controller.PPAController;
import octava.dto.MediaDto;
import octava.dto.ProjectDto;
import octava.model.observation.ReceiverDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import octava.service.RinexFileService;
import octava.service.RinexReader;
import octava.service.impl.observations.processors.impl.EnhancedSectionService;
import octava.util.plot.Figure;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Component
public class PPAFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PPAController.class);

    @Resource
    private RinexFileService rinexFileService;

    @Resource
    private RinexReader rinexReader;

    @Resource
    private EnhancedSectionService enhancedSectionService;

    @PostConstruct
    private void init() {

    }

    public void process(ProjectDto projectDto) {
        LOG.info("PPA perform data processing. Uploaded " + projectDto.getNumberOfFiles() + "file/s");
        List<MediaDto> files = projectDto.getRinexFiles();
        for (MediaDto rinexFile : files) {
            LOG.info("Processing " + rinexFile.getFileName() + " file...");
            try {
                LOG.info("Reading RINEX data...");

//                ReceiverDataModel rdm = rinexReader.read(file);

//                sectionFinder.find(rdm);

                Figure fig = new Figure("Observations", "Time, sec", "Pseudo distance");
//                fig.plot(rdm, ObsType.C1);
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
}

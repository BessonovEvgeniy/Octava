package ppa.controller;

import business.model.process.Process;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ppa.config.injector.InjectLog;
import ppa.config.injector.InjectThreadPool;
import ppa.model.PpaResult;
import ppa.model.observation.ReceiverDataModel;
import ppa.model.observation.header.impl.ObsType;
import ppa.service.RinexService;
import ppa.service.impl.observations.processors.FindSectionsService;
import utils.plot.Figure;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping(value = "/ppa")
public class PPAController implements ProcessorState {

    private RinexService rinexService;

    private FindSectionsService findSectionsService;

    @InjectThreadPool
    private ExecutorService executor;

    @InjectLog
    private Logger log;

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public PpaResult process(@Valid Process process, BindingResult bindingResult, HttpServletRequest request) throws Exception {
        log.info("PPA perform data processing. Uploaded " + process.getNumberOfFiles() + "file/s");
        List<File> files = process.getFiles();
        for (File file : files) {
            log.info("Processing " + file.getName() + " file...");
            try {
                log.info("Reading RINEX data...");
                ReceiverDataModel rdm = rinexService.readRinex(file);
                findSectionsService.findAbsolute(rdm);
                Figure fig = new Figure("Observations", "Time, sec", "Pseudo distance");
                fig.plot(rdm, ObsType.C1);
                //Process WAVELENGTH FACT L1/2

                //Determine Data Rate

                //Shift whole data to Integer seconds

                //Ephemeris processing

                //First positioning approach

                //PlotCAtest

            } catch (Exception e) {
                log.warn("File " + file.getAbsolutePath() + " isn't processed due to some problems. File skipped.");
                e.printStackTrace();
            }
        }
        return null;
    }

    @Autowired
    public void setRinexService(RinexService rinexService) {
        this.rinexService = rinexService;
    }

    @Autowired
    public void setFindSectionsService(FindSectionsService findSectionsService) {
        this.findSectionsService = findSectionsService;
    }
}

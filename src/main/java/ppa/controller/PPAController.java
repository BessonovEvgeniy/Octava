package ppa.controller;

import business.model.process.Process;
import ppa.config.injector.InjectLog;
import ppa.config.injector.InjectThreadPool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ppa.model.observation.ReceiverDataModel;
import ppa.model.observation.header.impl.ObsType;
import ppa.service.RinexService;
import ppa.service.impl.observations.processors.FindSectionsService;
import utils.plot.Figure;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Controller
public class PPAController implements ProcessorState {

    private RinexService rinexService;

    private FindSectionsService findSectionsService;

    @InjectThreadPool
    private ExecutorService executor;

    @InjectLog
    private Logger log;

    public void process(Process process) throws Exception {
        List<File> files = process.getFiles();
        for (File file : files) {
            try {
                //Read and pre process RINEX data
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

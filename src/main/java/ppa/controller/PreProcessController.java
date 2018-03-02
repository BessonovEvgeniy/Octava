package ppa.controller;

import Jama.Matrix;
import business.model.process.Process;
import config.injector.InjectThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ppa.model.observation.ReceiverDataModel;
import ppa.model.observation.header.impl.ObsType;
import ppa.service.RinexService;
import utils.plot.Figure;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Controller
public class PreProcessController implements ProcessorState {

    @Autowired
    private RinexService rinexService;

    @InjectThreadPool
    private ExecutorService executor;

    public void process(Process process) throws Exception {
        List<ReceiverDataModel> dataModels = rinexService.readRinex(process);

        for (ReceiverDataModel dataModel : dataModels) {
            dataModel.buildObsMatrixFromRawData();

            Matrix matrix = dataModel.getObs().get(ObsType.C1);

            Figure figure = new Figure();
            figure.plotRows(matrix);
        }
    }
}

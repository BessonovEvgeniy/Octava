package ppa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ppa.model.observation.ReceiverDataModel;
import business.model.process.Process;
import ppa.service.RinexService;

import java.util.List;

@Controller
public class PreProcessController implements ProcessorState {

    @Autowired
    private RinexService rinexService;

    public void process(Process process) throws Exception {
        List<ReceiverDataModel> dataModels = rinexService.readRinex(process);
    }
}

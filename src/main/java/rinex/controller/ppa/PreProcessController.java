package rinex.controller.ppa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import rinex.controller.processor.ProcessorState;
import rinex.model.observation.ReceiverDataModel;
import rinex.model.process.Process;
import rinex.service.RinexService;

import java.util.List;

@Controller
public class PreProcessController implements ProcessorState {

    @Autowired
    private RinexService rinexService;

    public void process(Process process) throws Exception {
        List<ReceiverDataModel> dataModels = rinexService.readRinex(process);
    }
}

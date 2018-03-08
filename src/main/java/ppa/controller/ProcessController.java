package ppa.controller;

import business.model.process.Process;
import config.injector.InjectThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutorService;

@Controller
public class ProcessController {

    private PreProcessController controller;

    @InjectThreadPool
    private ExecutorService executor;

    @Autowired
    ProcessController(PreProcessController controller) {
        this.controller = controller;
    }

    public void process(Process process){
        Runnable ppa = () -> {
            try {
                controller.process(process);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        executor.submit(ppa);
    }
}

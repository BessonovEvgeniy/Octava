package ppa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import business.model.process.Process;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class ProcessController {

    @Autowired
    private PreProcessController controller;

    private ExecutorService executor = Executors.newFixedThreadPool(5);

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

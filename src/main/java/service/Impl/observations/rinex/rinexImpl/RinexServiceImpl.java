package service.Impl.observations.rinex.rinexImpl;

import model.observations.ReceiverDataModel;
import org.springframework.stereotype.Service;

import service.RinexService;
import service.State;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RinexServiceImpl implements RinexService {

    private BufferedReader reader;
    private State state;
    private ReceiverDataModel data = new ReceiverDataModel();

    public ReceiverDataModel readRinex(String fileName) throws IOException {

        Path path = Paths.get(fileName);

        if (Files.exists(path)) {
            reader = new BufferedReader(new FileReader(fileName));
            changeState(new ReadHeaderImpl());
            state.read(reader, data);
            changeState(new ReadRinexObservationsDecorator());
            state.read(reader, data);
            //TODO Change return thp; -> return TNP.NULL;
            return data;
        } else {
            return data;
        }
    }

    private void changeState(State state){
        this.state = state;
    }


}
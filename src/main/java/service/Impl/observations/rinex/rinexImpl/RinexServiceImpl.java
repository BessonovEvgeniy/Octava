package service.Impl.observations.rinex.rinexImpl;

import model.observations.ReceiverDataModel;
import org.springframework.stereotype.Service;
import service.RinexService;
import service.State;

import java.io.BufferedReader;

@Service
public class RinexServiceImpl implements RinexService {

    private State state;
    private ReceiverDataModel data = new ReceiverDataModel();

    public ReceiverDataModel readRinex(BufferedReader reader) throws Exception {

        if (reader != null) {
            changeState(new ReadHeaderImpl());
            state.read(reader, data);
            changeState(new ReadRinexObservationsDecorator());
            state.read(reader, data);
            return data;
        } else {
            return data;
        }
    }

    private void changeState(State state){
        this.state = state;
    }


}
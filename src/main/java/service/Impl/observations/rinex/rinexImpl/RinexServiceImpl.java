package service.Impl.observations.rinex.rinexImpl;

import model.observations.ReceiverDataModel;
import org.springframework.stereotype.Service;
import service.RinexService;
import service.State;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class RinexServiceImpl implements RinexService {

    private State state;
    private ReceiverDataModel data;

    public ReceiverDataModel readRinex(InputStream inputStream) throws Exception {
        if (inputStream == null) {

            return ReceiverDataModel.NULL;

        } else {
            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(inputStream))) {

                data = new ReceiverDataModel();

                changeState(new ReadHeaderImpl());
                state.read(reader, data);
                changeState(new ReadRinexObservationsDecorator());
                state.read(reader, data);
            }
            return data;
        }
    }

    private void changeState(State state){
        this.state = state;
    }
}
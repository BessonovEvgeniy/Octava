package rinex.service.Impl.observations.rinex.rinexImpl;

import rinex.model.observations.ReceiverDataModel;
import org.springframework.stereotype.Service;
import rinex.service.Impl.observations.rinex.rinexImpl.header.RinexHeaderException;
import rinex.service.State;
import rinex.service.RinexService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class RinexServiceImpl implements RinexService {

    private State state;

    public ReceiverDataModel readRinex(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return ReceiverDataModel.NULL;
        } else {
            ReceiverDataModel data = new ReceiverDataModel();
            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(inputStream))) {


                changeState(new ReadHeaderImpl());
                state.read(reader, data);
                changeState(new ReadRinexObservationsDecorator());
                state.read(reader, data);
            } catch (RinexHeaderException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw new Exception();
            }
            return data;
        }
    }

    private void changeState(State state){
        this.state = state;
    }
}
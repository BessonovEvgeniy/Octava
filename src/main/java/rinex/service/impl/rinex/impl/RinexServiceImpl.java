package rinex.service.impl.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rinex.exception.UnknownHeaderLabelException;
import rinex.model.observation.ReceiverDataModel;
import rinex.service.RinexService;
import rinex.service.State;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class RinexServiceImpl implements RinexService {

    private State state;

    private ReceiverDataModel data;

    @Autowired
    private ReadHeaderImpl readHeader;

    public ReceiverDataModel readRinex(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return ReceiverDataModel.NULL;
        } else {
            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(inputStream))) {

                changeState(readHeader);
                state.read(reader, data);
                changeState(new ReadRinexObservationsDecorator());
                state.read(reader, data);
            } catch (UnknownHeaderLabelException e) {
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
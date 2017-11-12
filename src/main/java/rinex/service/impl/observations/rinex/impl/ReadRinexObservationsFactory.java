package rinex.service.impl.observations.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rinex.model.observations.ReceiverDataModel;
import rinex.service.State;

import java.util.HashMap;
import java.util.Map;

@Service
class ReadRinexObservationsFactory {

    @Autowired
    private Map<String, State> rinexObservationReaders;

    private ReceiverDataModel dataModel;

    @Autowired
    ReadRinexObservationsFactory(ReceiverDataModel model) {
        dataModel = model;
    }

    public State getReadRinexObservationsReader(String version) {
        return rinexObservationReaders.get(version);
    }
}
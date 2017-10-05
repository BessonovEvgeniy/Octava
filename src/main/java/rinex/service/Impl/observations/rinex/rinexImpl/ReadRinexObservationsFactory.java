package rinex.service.Impl.observations.rinex.rinexImpl;

import rinex.model.observations.ReceiverDataModel;
import rinex.service.State;

import java.util.HashMap;
import java.util.Map;

class ReadRinexObservationsFactory {

    static Map<String, State> rinexObservationReaders = new HashMap<String, State>();

    private ReceiverDataModel dataModel;

    ReadRinexObservationsFactory(ReceiverDataModel model) {
        dataModel = model;
        init();
    }

    private void init() {
        rinexObservationReaders.put("2.11", new ReadRinexObservationsV211Impl(dataModel));
    }
}
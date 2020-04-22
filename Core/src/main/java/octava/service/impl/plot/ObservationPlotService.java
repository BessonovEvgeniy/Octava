package octava.service.impl.plot;

import octava.model.observation.ReceiverDataModel;

public interface ObservationPlotService {

    void plotAbsoluteObservations(final ReceiverDataModel receiverDataModel);

    void plotAbsoluteCodeObservations(final ReceiverDataModel receiverDataModel);
}

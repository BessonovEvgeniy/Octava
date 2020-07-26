package octava.service;

import octava.model.observation.ReceiverDataModel;

public interface ObservationRateService extends CorrectionService {

    void determineObservationRate(ReceiverDataModel receiverData);

    void applyObservationRate(ReceiverDataModel receiverData);

}

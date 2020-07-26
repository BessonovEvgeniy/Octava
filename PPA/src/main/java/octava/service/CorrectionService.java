package octava.service;

import octava.model.observation.ReceiverDataModel;

public interface CorrectionService {

    void applyCorrection(ReceiverDataModel receiverData);
}

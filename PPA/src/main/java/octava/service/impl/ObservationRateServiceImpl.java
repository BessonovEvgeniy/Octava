package octava.service.impl;

import octava.model.observation.Correction;
import octava.model.observation.ReceiverDataModel;
import octava.service.ObservationRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("observationRateService")
public class ObservationRateServiceImpl implements ObservationRateService {

    private static final Logger LOG = LoggerFactory.getLogger(ObservationRateServiceImpl.class);

    @Override
    public void applyCorrection(final ReceiverDataModel receiverData) {
        determineObservationRate(receiverData);
        applyObservationRate(receiverData);
    }

    @Override
    public void determineObservationRate(final ReceiverDataModel receiverData) {
        LOG.error("Method not implemented");
    }

    @Override
    public void applyObservationRate(final ReceiverDataModel receiverData) {
        LOG.error("Method not implemented");
        receiverData.addAppliedCorrection(Correction.EPOCH_RATE);
    }
}

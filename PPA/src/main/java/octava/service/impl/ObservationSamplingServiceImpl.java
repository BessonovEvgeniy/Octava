package octava.service.impl;

import octava.model.observation.Correction;
import octava.model.observation.ReceiverDataModel;
import octava.service.ObservationSamplingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("observationSamplingService")
public class ObservationSamplingServiceImpl implements ObservationSamplingService {

    private static final Logger LOG = LoggerFactory.getLogger(ObservationSamplingServiceImpl.class);

    @Override
    public void applyCorrection(final ReceiverDataModel receiverData) {
        LOG.error("Method not implemented");

        receiverData.addAppliedCorrection(Correction.SAMPLING);
    }
}

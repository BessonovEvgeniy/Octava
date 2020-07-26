package octava.service.impl.correction;

import octava.model.observation.Correction;
import octava.model.observation.ReceiverDataModel;
import octava.service.WaveLengthFactorCorrectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("waveLengthFactorCorrectionService")
public class WaveLengthFactorCorrectionServiceImpl implements WaveLengthFactorCorrectionService {

    private static final Logger LOG = LoggerFactory.getLogger(WaveLengthFactorCorrectionServiceImpl.class);

    @Override
    public void applyCorrection(final ReceiverDataModel receiverDataModel) {
        LOG.error("method not implemented");

        receiverDataModel.addAppliedCorrection(Correction.WAVE_LENGTH_FACTOR);
    }
}

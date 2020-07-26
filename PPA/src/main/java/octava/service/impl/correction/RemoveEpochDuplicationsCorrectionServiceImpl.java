package octava.service.impl.correction;

import octava.model.observation.Correction;
import octava.model.observation.ReceiverDataModel;
import octava.service.RemoveEpochDuplicationsCorrectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("removeEpochDuplicationsCorrectionService")
public class RemoveEpochDuplicationsCorrectionServiceImpl implements RemoveEpochDuplicationsCorrectionService {

    private static final Logger LOG = LoggerFactory.getLogger(RemoveEpochDuplicationsCorrectionServiceImpl.class);

    @Override
    public void applyCorrection(ReceiverDataModel receiverData) {
        LOG.error("method not implemented");

        receiverData.addAppliedCorrection(Correction.EPOCH_DUPLICATES);
    }
}

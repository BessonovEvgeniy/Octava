package octava.service.impl;

import octava.model.Status;
import octava.model.observation.ReceiverDataModel;
import octava.model.rinex.RinexFileMediaModel;
import octava.service.*;
import octava.service.impl.observations.processors.impl.EnhancedSectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service("ppaRinexReaderService")
public class PPARinexReaderServiceImpl implements RinexReaderService {

    private static final Logger LOG = LoggerFactory.getLogger(PPARinexReaderServiceImpl.class);

    @Resource
    private RinexReaderService rinexReaderService;

    @Resource
    private RinexMediaService rinexMediaService;

    @Resource
    private EnhancedSectionService enhancedSectionService;

    @Autowired
    private List<RinexCorrectionService> correctionServices;

    @Override
    public ReceiverDataModel read(final InputStream inputStream) {
        return rinexReaderService.read(inputStream);
    }

    @Override
    public ReceiverDataModel read(final File file) {

        LOG.info("Reading RINEX file {}", file.getPath());

        final RinexFileMediaModel rinexFile = rinexMediaService.getRinexFileByPath(file);

        rinexFile.setStatus(Status.PROCESSING);
        rinexMediaService.update(rinexFile);

        final ReceiverDataModel receiverDataModel = rinexReaderService.read(rinexFile);

        enhancedSectionService.find(receiverDataModel);

        correctionServices.forEach(service -> service.applyCorrection(receiverDataModel));

        return receiverDataModel;
    }

    @Override
    public ReceiverDataModel read(RinexFileMediaModel file) {
        return null;
    }
}

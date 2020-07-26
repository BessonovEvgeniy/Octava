package octava.facade.impl;

import octava.controller.PPAController;
import octava.dao.BaseRepository;
import octava.facade.PPAFacade;
import octava.model.Status;
import octava.model.observation.ReceiverDataModel;
import octava.service.CorrectionService;
import octava.service.RinexReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.text.MessageFormat;

import static java.util.Objects.nonNull;

@Component
public class PPAFacadeImpl implements PPAFacade {

    private static final Logger LOG = LoggerFactory.getLogger(PPAController.class);

    @Resource(name = "graphicalPPARinexReaderService")
    private RinexReaderService rinexReaderService;

    @Resource
    private BaseRepository<ReceiverDataModel> baseRepository;

    @Resource
    private CorrectionService observationRateService;

    @Resource
    private CorrectionService observationSamplingService;

    @Async
    @Override
    public void process(final File file) {

        LOG.info("Processing " + file.getName() + " file...");

        ReceiverDataModel receiverData = null;

        try {

            receiverData = rinexReaderService.read(file);

            beforeProcess(receiverData);

            observationRateService.applyCorrection(receiverData);

            observationSamplingService.applyCorrection(receiverData);


            //Ephemeris processing

            //First positioning approach

            //PlotCAtest

            afterProcess(receiverData);

        } catch (Exception e) {
            LOG.error("File " + file.getAbsolutePath() + " isn't processed due to some problems. File skipped.", e);
            failProcess(receiverData);
        }
    }

    protected void beforeProcess(final ReceiverDataModel receiverData) {
        LOG.info(MessageFormat.format("Rinex file {0} is processing", receiverData.getFileName()));

        receiverData.setStatus(Status.PROCESSING);

        baseRepository.save(receiverData);
    }

    protected void failProcess(final ReceiverDataModel receiverData) {
        if (nonNull(receiverData)) {

            receiverData.setStatus(Status.FAILED);

            LOG.info(MessageFormat.format("Rinex file {0} processing is being failed", receiverData.getFileName()));

            baseRepository.save(receiverData);
        }
    }

    protected void afterProcess(final ReceiverDataModel receiverData) {
        receiverData.setStatus(Status.PROCESSED);

        LOG.info(MessageFormat.format("Rinex file {0} is processed", receiverData.getFileName()));

        baseRepository.save(receiverData);
    }
}

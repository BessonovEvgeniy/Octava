package octava.service.impl;

import octava.model.observation.ReceiverDataModel;
import octava.model.rinex.RinexFileMediaModel;
import octava.service.RinexReaderService;
import octava.service.impl.plot.ObservationPlotService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;

@Service("graphicalPPARinexReaderService")
public class GraphicalPPARinexReaderServiceImpl implements RinexReaderService {

    @Resource(name = "ppaRinexReaderService")
    private RinexReaderService rinexReaderService;

    @Resource
    private ObservationPlotService observationPlotService;

    @Override
    public ReceiverDataModel read(final InputStream inputStream) {

        final ReceiverDataModel receiverDataModel = rinexReaderService.read(inputStream);

        plot(receiverDataModel);

        return receiverDataModel;
    }

    @Override
    public ReceiverDataModel read(final File file) {
        final ReceiverDataModel receiverDataModel = rinexReaderService.read(file);

        plot(receiverDataModel);

        return receiverDataModel;
    }

    @Override
    public ReceiverDataModel read(final RinexFileMediaModel file) {
        final ReceiverDataModel receiverDataModel = rinexReaderService.read(file);

        plot(receiverDataModel);

        return receiverDataModel;
    }

    private void plot(final ReceiverDataModel receiverDataModel) {
        observationPlotService.plotAbsoluteObservations(receiverDataModel);
    }
}

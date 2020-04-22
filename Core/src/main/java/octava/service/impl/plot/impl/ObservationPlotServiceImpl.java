package octava.service.impl.plot.impl;

import octava.model.observation.ReceiverDataModel;
import octava.model.observation.header.impl.ObsType;
import octava.service.impl.plot.ObservationPlotService;
import octava.util.plot.Figure;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ObservationPlotServiceImpl implements ObservationPlotService {

    @Async
    public void plotAbsoluteObservations(final ReceiverDataModel receiverDataModel) {
        plotAbsoluteCodeObservations(receiverDataModel);
    }

    @Async
    public void plotAbsoluteCodeObservations(final ReceiverDataModel receiverDataModel) {
        final Figure fig = new Figure("Observations", "Time, sec", "Pseudo distance");
        fig.plot(receiverDataModel, ObsType.C1);
        fig.display();
    }

}

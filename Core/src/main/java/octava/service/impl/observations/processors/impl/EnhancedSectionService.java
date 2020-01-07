package octava.service.impl.observations.processors.impl;

import octava.model.observation.ReceiverDataModel;
import octava.model.observation.header.impl.ObsType;
import octava.model.Gnss;
import octava.model.rinex.Observations;
import org.apache.commons.math3.linear.RealMatrix;
import org.springframework.stereotype.Service;
import octava.util.matrix.MatrixToolSet;
import octava.util.time.SectionFinder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EnhancedSectionService {

    public void find(ReceiverDataModel rdm) {
        Map<ObsType, Observations> obs = rdm.getObs();
        List<LocalDateTime> time = rdm.getTime();

        RealMatrix flags = MatrixToolSet.ones(time.size(), Gnss.MAX_SAT);

        for (Map.Entry<ObsType, Observations> typedObs : obs.entrySet()) {
            for (int e = 0; e < time.size(); e++) {
                LocalDateTime epochTime = time.get(e);
                RealMatrix obsByEpoches = typedObs.getValue().getEpoch(epochTime);
                for (int i = 0; i < Gnss.MAX_SAT; i++) {
                    if (obsByEpoches.getEntry(0,i) == 0) {
                        flags.setEntry(e,i,0);
                    }
                }
            }
        }

        SectionFinder.SectionsData sections = new SectionFinder(flags, rdm.getObservationMode()).find();
        rdm.setSections(sections);
    }
}

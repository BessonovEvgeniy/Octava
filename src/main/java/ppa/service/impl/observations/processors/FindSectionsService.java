package ppa.service.impl.observations.processors;

import org.apache.commons.math3.linear.RealMatrix;
import org.springframework.stereotype.Service;
import ppa.model.observation.ReceiverDataModel;
import ppa.model.observation.header.impl.ObsType;
import ppa.model.rinex.Gnss;
import ppa.model.rinex.Observations;
import utils.matrix.MatrixToolSet;
import utils.time.Sections;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class FindSectionsService {

    public ReceiverDataModel findAbsolute(ReceiverDataModel rdm) {

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

        Sections.SectionsData sections = new Sections(flags, rdm.getObservationMode()).findSections();
        rdm.setSections(sections);
        return rdm;
    }

    public Sections find(ReceiverDataModel rdm, ObsType type) {
        throw new RuntimeException("Method is not implemented.");
    }
}

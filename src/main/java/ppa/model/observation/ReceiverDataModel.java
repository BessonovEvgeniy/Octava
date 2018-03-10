package ppa.model.observation;

import Jama.Matrix;
import com.google.common.primitives.Ints;
import config.AppInitializer;
import config.HibernateConfiguration;
import config.MvcConfiguration;
import config.injector.LogInjector;
import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ppa.dto.EpochDto;
import ppa.model.observation.header.impl.*;
import ppa.model.rinex.Gnss;
import ppa.model.rinex.Observations;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Scope("prototype")
public @Data class ReceiverDataModel implements Gnss {

    public static final ReceiverDataModel NULL = new NullReceiverDataModel();

    private RinexVersionType rinexVersionType;

    private PgmRunByDate pgmRunByDate;

    private MarkerName markerName;

    private ObserverAgency observerAgency;

    private RecTypeVers recTypeVers;

    private AntType antType;

    private ApproxPos approxPos;

    private AntennaDelta antennaDelta;

    private WaveLengthFact wavelengthFact;

    private TypesOfObs typesOfObs;

    private LeapSeconds leapSeconds;

    private Map<ObsType, Matrix> obs = new LinkedHashMap<>();

    private List<Integer> seconds = new LinkedList<>();

    private List<LocalDateTime> time = new LinkedList<>();

    private Map<ObsType, Observations> rawObs = new LinkedHashMap<>();

    private Set<Double> epoch;

    private int numberOfObsTypes;

    public void buildObsMatrixFromRawData(){

        if (rawObs == null) {
            return;
        }

        for (Map.Entry<ObsType, Observations> obsByType : rawObs.entrySet()) {

            Observations rawEpoches = obsByType.getValue();

            if (rawEpoches == null || rawEpoches.getObs() == null) {
                continue;
            }

            Matrix matrix = new Matrix(rawEpoches.size(), Gnss.MAX_SAT, 0);

            int epochCounter = 0;
            for (Map.Entry<LocalDateTime, Matrix> rawEpoch : rawEpoches.getObs().entrySet()) {

                matrix.setMatrix(epochCounter,epochCounter,0, Gnss.MAX_SAT-1, rawEpoch.getValue());
                epochCounter++;
            }

            obs.put(obsByType.getKey(), matrix);
        }

    }

    public void addObservations(EpochDto epoch) {
        if (epoch == null) {
            return;
        }

        for (String svCode : epoch.getSatellites()) {

            LocalDateTime epochTime = epoch.getEpochTime();
            int satellite = Ints.tryParse(svCode.substring(1,3));

            for (int index = 0; index < typesOfObs.size(); index++) {
                ObsType type = typesOfObs.get(index);
                Observations observations = rawObs.get(type);

                if (observations == null) {
                    observations = new Observations(type);
                    rawObs.put(type, observations);
                }

                Matrix epochArray = observations.getEpoch(epochTime);

                if (epochArray == null) {
                    epochArray = new Matrix(1, Gnss.MAX_SAT, 0);
                    observations.putEpoch(epochTime, epochArray);
                }

                double obsValue = epoch.getEpochData(index, svCode);
                epochArray.set(0, satellite, obsValue);

                observations.putEpoch(epochTime, epochArray);
                observations.putFlag(epochTime, epoch.getFlag());
            }
        }
    }

    public int getNumberOfObsTypes() {
        return (numberOfObsTypes == 0) ? numberOfObsTypes = typesOfObs.size() : numberOfObsTypes;
    }

    private static class NullReceiverDataModel extends ReceiverDataModel {
        @Override
        public String toString() {
            return "NullReceiverDataModel";
        }
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppInitializer.class);
        context.register(MvcConfiguration.class);
        context.register(HibernateConfiguration.class);
        context.register(BeanFactory.class);
        context.registerBean(LogInjector.class);
        context.scan("ppa", "utils", "config");

        ReceiverDataModel receiverDataModel1 = context.getBean(ReceiverDataModel.class);
        ReceiverDataModel receiverDataModel2 = context.getBean(ReceiverDataModel.class);

        Assert.isTrue(receiverDataModel1 != receiverDataModel2, "GOOD");
    }
}

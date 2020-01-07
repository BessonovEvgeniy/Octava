package octava.model.observation;

import com.google.common.primitives.Ints;
import octava.dto.EpochDto;
import lombok.Data;
import octava.model.observation.header.impl.*;
import octava.model.Gnss;
import octava.model.rinex.Observations;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import octava.util.time.SectionFinder;

import java.time.LocalDateTime;
import java.util.*;

@Component("receiverDataModel")
@Scope("prototype")
public @Data class ReceiverDataModel implements Gnss {

    public static final ReceiverDataModel NULL = new NullReceiverDataModel();

    private ObservationMode observationMode;

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

    private List<Integer> seconds = new LinkedList<>();

    private List<LocalDateTime> time = new LinkedList<>();

    private Map<ObsType, Observations> obs = new LinkedHashMap<>();

    private Set<Double> epoch;

    private SectionFinder.SectionsData sections;

    private int numberOfObsTypes;

    public void addObservations(EpochDto epoch) {
        if (epoch == null) {
            return;
        }
        LocalDateTime epochTime = epoch.getEpochTime();
        if (!time.contains(epochTime)) {
            time.add(epochTime);
        }
        for (String svCode : epoch.getSatellites()) {
            int satellite = Ints.tryParse(svCode.substring(1, 3));

            for (int index = 0; index < typesOfObs.size(); index++) {

                ObsType type = typesOfObs.get(index);
                Observations observations = obs.get(type);

                if (observations == null) {
                    observations = new Observations(type);
                    obs.put(type, observations);
                }

                RealMatrix epochArray = observations.getEpoch(epochTime);
                if (epochArray == null) {
                    epochArray = new Array2DRowRealMatrix(1, Gnss.MAX_SAT);
                    observations.putEpoch(epochTime, epochArray);
                }

                double obsValue = epoch.getEpochData(index, svCode);
                epochArray.setEntry(0, satellite, obsValue);

                observations.putEpoch(epochTime, epochArray);
                observations.putFlag(epochTime, epoch.getFlag());
            }
        }
    }

    public SectionFinder.SectionsData getSections() {
        return sections;
    }

    public ObservationMode getObservationMode() {
        return observationMode;
    }

    public void setObservationMode(ObservationMode observationMode) {
        this.observationMode = observationMode;
    }

    public RinexVersionType getRinexVersionType() {
        return rinexVersionType;
    }

    public void setRinexVersionType(RinexVersionType rinexVersionType) {
        this.rinexVersionType = rinexVersionType;
    }

    public PgmRunByDate getPgmRunByDate() {
        return pgmRunByDate;
    }

    public void setPgmRunByDate(PgmRunByDate pgmRunByDate) {
        this.pgmRunByDate = pgmRunByDate;
    }

    public MarkerName getMarkerName() {
        return markerName;
    }

    public void setMarkerName(MarkerName markerName) {
        this.markerName = markerName;
    }

    public ObserverAgency getObserverAgency() {
        return observerAgency;
    }

    public void setObserverAgency(ObserverAgency observerAgency) {
        this.observerAgency = observerAgency;
    }

    public RecTypeVers getRecTypeVers() {
        return recTypeVers;
    }

    public void setRecTypeVers(RecTypeVers recTypeVers) {
        this.recTypeVers = recTypeVers;
    }

    public AntType getAntType() {
        return antType;
    }

    public void setAntType(AntType antType) {
        this.antType = antType;
    }

    public ApproxPos getApproxPos() {
        return approxPos;
    }

    public void setApproxPos(ApproxPos approxPos) {
        this.approxPos = approxPos;
    }

    public AntennaDelta getAntennaDelta() {
        return antennaDelta;
    }

    public void setAntennaDelta(AntennaDelta antennaDelta) {
        this.antennaDelta = antennaDelta;
    }

    public WaveLengthFact getWavelengthFact() {
        return wavelengthFact;
    }

    public void setWavelengthFact(WaveLengthFact wavelengthFact) {
        this.wavelengthFact = wavelengthFact;
    }

    public TypesOfObs getTypesOfObs() {
        return typesOfObs;
    }

    public void setTypesOfObs(TypesOfObs typesOfObs) {
        this.typesOfObs = typesOfObs;
    }

    public LeapSeconds getLeapSeconds() {
        return leapSeconds;
    }

    public void setLeapSeconds(LeapSeconds leapSeconds) {
        this.leapSeconds = leapSeconds;
    }

    public List<Integer> getSeconds() {
        return seconds;
    }

    public void setSeconds(List<Integer> seconds) {
        this.seconds = seconds;
    }

    public List<LocalDateTime> getTime() {
        return time;
    }

    public void setTime(List<LocalDateTime> time) {
        this.time = time;
    }

    public Map<ObsType, Observations> getObs() {
        return obs;
    }

    public void setObs(Map<ObsType, Observations> obs) {
        this.obs = obs;
    }

    public Set<Double> getEpoch() {
        return epoch;
    }

    public void setEpoch(Set<Double> epoch) {
        this.epoch = epoch;
    }

    public void setSections(SectionFinder.SectionsData sections) {
        this.sections = sections;
    }

    public void setNumberOfObsTypes(int numberOfObsTypes) {
        this.numberOfObsTypes = numberOfObsTypes;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(antennaDelta.toString()).add(antType.toString()).add(approxPos.toString()).add(pgmRunByDate.toString()).
                add(rinexVersionType.toString());
        return joiner.toString();
    }

    public int getEpochSize() {
        return time.size();
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

    public Observations getObsByType(ObsType obsType) {
        return obs.get(obsType);
    }

    public enum ObservationMode {
        STATIC_MODE(1, 10, 50, 180), KINEMATIC_MODE(1, 20, 5, 180), STOP_AND_GO(0, 0, 0, 0), TEST_MODE(1, 3, 2, 5);

        private int delT;
        private int edgePoints;
        private int gap;
        private int sect;

        ObservationMode(int delT, int edgePoints, int gap, int sect) {
            this.delT = delT;
            this.edgePoints = edgePoints;
            this.gap = gap;
            this.sect = sect;
        }

        public int getEdgePoints() {
            return edgePoints;
        }

        public int getDelT() {
            return delT;
        }

        public int getGap() {
            return gap;
        }

        public int getSect() {
            return sect;
        }

        @Override
        public String toString() {
            return "Observation mode " + this.name();
        }
    }
}

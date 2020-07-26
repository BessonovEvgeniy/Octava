package octava.model.observation;

import com.google.common.primitives.Ints;
import lombok.Data;
import octava.dto.EpochDto;
import octava.model.BaseModel;
import octava.model.Gnss;
import octava.model.Status;
import octava.model.observation.header.impl.*;
import octava.model.rinex.ObservationsModel;
import octava.util.time.SectionFinder;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;

@Entity
@Table(name = "RECEIVER_DATA")
public @Data class ReceiverDataModel extends BaseModel implements Gnss {

    @Transient
    public static final ReceiverDataModel NULL = new NullReceiverDataModel();

    @Transient
    private SectionFinder.SectionsData sections;

    private Status status = Status.NEW;

    private File file;

    private ObservationMode observationMode;

    @ElementCollection
    private Set<Correction> appliedCorrections = new HashSet<>();

    @ElementCollection
    private List<Integer> seconds = new LinkedList<>();

    @ElementCollection
    private List<LocalDateTime> time = new LinkedList<>();

    @ElementCollection
    private Set<Double> epoch = new LinkedHashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private RinexVersionTypeModel rinexVersionType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private PgmRunByDateModel pgmRunByDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private MarkerNameModel markerName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private ObserverAgencyModel observerAgency;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private RecTypeVersModel recTypeVers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private AntTypeModel antType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private ApproxPosModel approxPos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private AntennaDeltaModel antennaDelta;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private WaveLengthFactorModel wavelengthFactor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private TypesOfObsModel typesOfObs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private LeapSecondsModel leapSeconds;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "RECEIVERDATA_OBSERVATIONS_MAPPING",
            joinColumns =
                @JoinColumn(name = "receiverdata_id", referencedColumnName = "id"),
            inverseJoinColumns =
                @JoinColumn(name = "observations_id", referencedColumnName = "id")
    )
    @MapKeyEnumerated
    private Map<ObsType, ObservationsModel> observations = new LinkedHashMap<>();

    private int numberOfObsTypes;

    private int observationRate;

    public String getFileName() {
        return isNull(getFile()) ? null : getFile().getName();
    }

    public void addAppliedCorrection(final Correction correction) {
        appliedCorrections.add(correction);
    }

    public void addObservations(final EpochDto epoch) {
        if (isNull(epoch)) {
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
                ObservationsModel observationsModel = this.observations.get(type);

                if (observationsModel == null) {
                    observationsModel = new ObservationsModel(type);
                    this.observations.put(type, observationsModel);
                }

                RealMatrix epochArray = observationsModel.getEpoch(epochTime);
                if (epochArray == null) {
                    epochArray = new Array2DRowRealMatrix(1, Gnss.MAX_SAT);
                    observationsModel.putEpoch(epochTime, epochArray);
                }

                double obsValue = epoch.getEpochData(index, svCode);
                epochArray.setEntry(0, satellite, obsValue);

                observationsModel.putEpoch(epochTime, epochArray);
                observationsModel.putFlag(epochTime, epoch.getFlag());
            }
        }
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(SPACE);
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

    public ObservationsModel getObsByType(ObsType obsType) {
        return observations.get(obsType);
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

package rinex.service.Impl.observations.rinex.rinexImpl.header;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import rinex.service.HeaderLabel;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Service
public class HeaderLabelFactory {

    private Map<String, HeaderLabel> headerLabels = new HashMap<>();

    public HeaderLabelFactory() {
        init();
    }

    @PostConstruct
    private void init() {
        headerLabels.put("RINEX VERSION / TYPE",new RinexVersionType());
        headerLabels.put("PGM / RUN BY / DATE", new PgmRunByDate());
        headerLabels.put("MARKER NAME",         new MarkerName());
        headerLabels.put("MARKER NUMBER",       new MarkerNumber());
        headerLabels.put("OBSERVER / AGENCY",   new ObserverAgency());
        headerLabels.put("REC # / TYPE / VERS", new RecTypeVers());
        headerLabels.put("ANT # / TYPE",        new AntType());
        headerLabels.put("APPROX POSITION XYZ", new ApproxPos());
        headerLabels.put("ANTENNA: DELTA H/E/N",new AntennaDelta());
        headerLabels.put("WAVELENGTH FACT L1/2",new WavelengthFact());
        headerLabels.put("# / TYPES OF OBSERV", new TypesOfObserv());
        headerLabels.put("INTERVAL",            new Interval());
        headerLabels.put("LEAP SECONDS",        new LeapSeconds());
        headerLabels.put("TIME OF FIRST OBS",   new TimeOfFirstObs());
        headerLabels.put("TIME OF LAST OBS",    new TimeOfLastObs());
        headerLabels.put("RCV CLOCK OFFS APPL", new RcvClockOffsAppl());
        headerLabels.put("# OF SATELLITES",     line -> false);
        headerLabels.put("PRN / # OF OBS",      line -> false);
        headerLabels.put("COMMENT",             line -> false);
        headerLabels.put("",                    line -> false);
    }

    @NotNull
    @Length(min = 80, max = 80)
    public HeaderLabel getHeaderLabel(String line) throws RinexHeaderException {

        String label = line.substring(60,line.length()).toUpperCase().trim();
        HeaderLabel headerLabel = headerLabels.get(label);
        if (headerLabel == null) {
            throw new RinexHeaderException("Unknown header label: " + label);
        } else {
            headerLabel.parse(line);
        }
        return headerLabel;
    }
}
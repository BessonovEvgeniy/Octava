package rinex.dto;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import rinex.model.observation.header.impl.ObsType;
import rinex.model.observation.header.impl.TypesOfObs;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static rinex.model.rinex.Gnss.MAX_SAT;

public @Data class EpochDto {

    private LocalDateTime localDateTime;

    private double gpsSeconds;

    private int flag;

    private int numSv;

    private boolean timeParsed;

    private List<String> time;                  //YY MM DD HH SS F NUM_SV

    private List<String> svPattern;

    private Map<String, List<String>> rawObs;   // SV vs Raw Observations

    @Autowired
    private TypesOfObs types;

    public double[] getObservations(ObsType type) throws Exception {

        boolean notEnoughOrIllegalData = ObjectUtils.isEmpty(rawObs) ||
                types == null || types.getObsTypes().isEmpty();

        if (notEnoughOrIllegalData) {
            throw new Exception();
        }
        double[] obsValues = new double[MAX_SAT];
        Integer ordinal = type.ordinal();

        for (String svName : svPattern) {
            if (type.isSystemRequired(svName)) {

                Integer sv = Integer.parseInt(svName.substring(1, svName.length()));
                List<String> svRawObs = rawObs.get(svName);
                obsValues[sv] = Double.parseDouble(svRawObs.get(ordinal));
            }
        }
        return obsValues;
    }

    public void parseTime() {
        if (!timeParsed) {
            timeParsed = true;
            try {
                if (time != null && !time.isEmpty()) {
                    Iterator<String> iter = time.iterator();
                    int year = Integer.parseInt(iter.next());
                    int month = Integer.parseInt(iter.next());
                    int day = Integer.parseInt(iter.next());
                    int hour = Integer.parseInt(iter.next());
                    int min = Integer.parseInt(iter.next());
                    Long sec = Long.parseLong(iter.next());
                    Long nanoSeconds = TimeUnit.SECONDS.convert(sec, TimeUnit.NANOSECONDS);

                    gpsSeconds = hour*3600 + min*60 + sec;

                    localDateTime = LocalDateTime.of(year, month, day, hour, min, sec.intValue(), nanoSeconds.intValue());

                    flag = Integer.parseInt(iter.next());
                    numSv = Integer.parseInt(iter.next());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

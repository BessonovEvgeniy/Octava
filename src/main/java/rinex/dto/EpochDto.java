package rinex.dto;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import rinex.model.observations.header.TypesOfObs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static rinex.model.rinex.Gnss.MAX_SAT;

@Component
@Scope("prototype")
public @Data class EpochDto {

    List<String> time;                  //YY MM DD HH SS F NUM_SV

    List<String> svPattern;

    Map<String, List<String>> rawObs;   // SV vs Raw Observations

    @Autowired
    TypesOfObs types;

    LocalDateTime localDateTime;

    double sec;

    double gpsSeconds;

    int flag;

    int numSv;

    boolean timeParsed;

    public double[] getObservations(TypesOfObs.Type type) throws Exception {

        boolean notEnoughOrIllegalData = rawObs == null || rawObs.isEmpty() ||
                types == null || types.getObsTypes().isEmpty();

        double[] obsValues = new double[MAX_SAT];
        if (notEnoughOrIllegalData) {
            throw new Exception();
        } else {
            Integer ordinal = type.getOrdinal(types.getObsTypes());

            svPattern.forEach(svName -> {
                if (type.isSystemRequired(svName)) {
                    Integer sv = Integer.parseInt(svName.substring(1,svName.length()));
                    List<String> svRawObs = rawObs.get(svName);
                    obsValues[sv] = Double.parseDouble(svRawObs.get(ordinal));
                }
            });
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

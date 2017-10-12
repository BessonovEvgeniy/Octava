package rinex.dto;


import lombok.Data;
import rinex.service.Impl.observations.rinex.rinexImpl.header.TypesOfObs;

import java.util.*;

import static rinex.model.rinex.Gnss.MAX_SAT;

public @Data class EpochDTO {

    List<String> time; //YY MM DD HH SS F NUM_SV
    List<String> svPattern;
    Map<String, List<String>> rawObs; // SV vs Raw Observations
    TypesOfObs types;
    Integer year;
    Integer month;
    Integer day;
    Integer hour;
    Integer min;
    Double sec;
    Integer flag;
    Integer numSv;

    boolean timeParsed;

    public EpochDTO(TypesOfObs obsTypes) {
        types = obsTypes;
    }

    public double[] getObservations(TypesOfObs.Type type) throws Exception {

        boolean notEnoughOrIllegalData = rawObs == null || rawObs.isEmpty() ||
                types == null || types.getObsTypes().isEmpty();

        double[] obsValues = new double[MAX_SAT];
        if (notEnoughOrIllegalData) {
            throw new Exception();
        } else {
            Integer ordinal = type.getOrdinal(types.getObsTypes());

            for(int svIndex = 0; svIndex < svPattern.size(); svIndex++) {
                String svName = svPattern.get(svIndex);
                if (type.isSystemRequired(svName)) {
                    Integer sv = Integer.parseInt(svName.substring(1,svName.length()));
                    List<String> svRawObs = rawObs.get(svName);
                    obsValues[sv] = Double.parseDouble(svRawObs.get(ordinal));
                }
            }
        }
        return obsValues;
    }

    public void parseTime() {
        if (timeParsed) {
            return;
        } else {
            timeParsed = true;
            try {
                if (time != null && !time.isEmpty()) {
                    Iterator<String> iter = time.iterator();
                    String yearString = iter.next();
                    yearString = yearString.length() == 2 ? "20" + yearString : yearString;
                    year = Integer.parseInt(yearString);
                    month = Integer.parseInt(iter.next());
                    day = Integer.parseInt(iter.next());
                    hour = Integer.parseInt(iter.next());
                    min = Integer.parseInt(iter.next());
                    sec = Double.parseDouble(iter.next());

                    flag = Integer.parseInt(iter.next());
                    numSv = Integer.parseInt(iter.next());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

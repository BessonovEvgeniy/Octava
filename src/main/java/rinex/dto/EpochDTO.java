package rinex.dto;


import lombok.Data;
import rinex.model.rinex.Gnss;
import rinex.model.rinex.Observations;
import rinex.service.Impl.observations.rinex.rinexImpl.header.TypesOfObserv;

import java.util.*;

import static rinex.model.rinex.Gnss.MAX_GPS_SAT;

public @Data class EpochDTO {

    List<String> time; //YY MM DD HH SS F NUM_SV
    List<String> svPattern;
    List<List<String>> rawObs;
    TypesOfObserv types;
    Integer year;
    Integer month;
    Integer day;
    Integer hour;
    Integer min;
    Double sec;
    Integer flag;
    Integer numSv;

    boolean timeParsed;

//  system -> dataType -> data

    public EpochDTO(TypesOfObserv obsTypes) {
        types = obsTypes;
    }

    public double[] getObservations(Observations.GnssSystem system, Observations.Type type) throws Exception {

        boolean notEnoughOrIllegalData = rawObs == null || rawObs.isEmpty() ||
                types == null || types.getObsTypes().isEmpty();

        double[] obsValues = new double[MAX_GPS_SAT];
        if (notEnoughOrIllegalData) {
            throw new Exception();
        } else {
            for(int svIndex = 0; svIndex < svPattern.size(); svIndex++) {
                String currentSv = svPattern.get(svIndex);
                if (currentSv.contains(system.getCode())) {
                    Integer sv = Integer.parseInt(currentSv.substring(1,currentSv.length()));
                    List<String> obsTypes = types.getObsTypes();
                    List<String> svRawObs = rawObs.get(svIndex);
                    for (int j = 0; j < obsTypes.size(); j++ ) {
                        if (obsTypes.get(j).contains(type.name())) {
                            obsValues[sv] = Double.parseDouble(svRawObs.get(j));
                            break;
                        }
                    }

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

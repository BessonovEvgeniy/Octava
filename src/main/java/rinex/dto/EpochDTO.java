package rinex.dto;

import lombok.Data;
import rinex.model.rinex.Observations;
import rinex.service.Impl.observations.rinex.rinexImpl.header.TypesOfObserv;

import java.util.*;

public @Data class EpochDTO {

    List<String> time; //YY MM DD HH SS F NUM_SV
    Map<String, List<String>> rawObs; //SV vs Params
    Map<Observations.System, Integer> sv; //GNSS System vs Sv num
    Map<Observations.Type, Double> obs;  //Type vs Params
    TypesOfObserv types;
    Integer year;
    Integer month;
    Integer day;
    Integer hour;
    Integer min;
    Double sec;
    Integer flag;
    Integer numSv;

    public EpochDTO(TypesOfObserv obsTypes) {
        types = obsTypes;
    }

    public void parseRawData() {
        try {
            if (time != null && !time.isEmpty()) {
                Iterator<String> iter = time.iterator();
                String yearString = iter.next();
                if (yearString.length() == 2) {
                    yearString = "20" + yearString;
                }
                year = Integer.parseInt(yearString);
                month = Integer.parseInt(iter.next());
                day = Integer.parseInt(iter.next());
                hour = Integer.parseInt(iter.next());
                min = Integer.parseInt(iter.next());
                sec = Double.parseDouble(iter.next());

                flag = Integer.parseInt(iter.next());
                numSv = Integer.parseInt(iter.next());
            }

            Boolean notEnoughOrIllegalData = rawObs == null || rawObs.isEmpty() ||
                    types == null || types.getObsTypes().isEmpty()
                    || types.getObsTypes().size() != obs.size();

            if (notEnoughOrIllegalData) {
                throw new Exception();
            } else {
/*                for (Observations.Type item : types) {
                    //TODO finish epoch DTO parser
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

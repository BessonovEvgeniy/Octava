package rinex.service.impl.rinex.impl;

import org.springframework.stereotype.Service;
import rinex.dto.EpochDto;
import rinex.model.observations.ReceiverDataModel;
import rinex.model.rinex.Observations;
import rinex.model.observations.header.impl.TypesOfObs;
import rinex.service.State;

import java.io.BufferedReader;
import java.util.*;

@Service("2.11")
class ReadRinexObservationsV211Impl extends AbstractReadRinexObservations implements State {

    @Override
    protected EpochDto readEpoch(BufferedReader reader) throws Exception {
        TypesOfObs types = model.getTypesOfObs();

        EpochDto epochDto = new EpochDto();

        Map<String, List<String>> obs = new LinkedHashMap<>();
        try {
            String timeData = line.substring(0,32);
            String satData = line.substring(32, line.length());

            List<String> time = splitDataBySpace(timeData);
            List<String> sat = splitSatDataHeader(satData);

            Integer obsFlag = Integer.parseInt(time.get(time.size() - 1));
            Integer numSv = Integer.parseInt(time.get(time.size() - 1));

            for (Integer sv = 0; sv < numSv; sv++) {
                if ((line = reader.readLine()) != null) {
                    obs.put(sat.get(sv), splitDataBySpace(line));
                } else {
                    throw new Exception();
                }
            }
            epochDto.setTime(time);
            epochDto.setFlag(obsFlag);
            epochDto.setNumSv(numSv);
            epochDto.setSvPattern(sat);
            epochDto.setRawObs(obs);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return epochDto;
    }

    @Override
    public void read(BufferedReader reader, ReceiverDataModel data) throws Exception {
        while ((line = reader.readLine()) != null) {
            if (!line.contains("COMMENT")) {
                EpochDto epochDto = readEpoch(reader);

                Map<TypesOfObs.Type, Observations> allObs = data.getObs();

                for (TypesOfObs.Type type : data.getTypesOfObs().getObsTypes()) {
                    Observations obs;
                    if (allObs.get(type) == null) {
                        obs = new Observations(type);
                        allObs.put(type, obs);
                    } else {
                        obs = allObs.get(type);
                    }
                    System.out.println(line);
                    obs.add(epochDto.getObservations(type));
                }
            }
        }
    }
}
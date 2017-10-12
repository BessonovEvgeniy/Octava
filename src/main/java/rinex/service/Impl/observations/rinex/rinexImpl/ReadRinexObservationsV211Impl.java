package rinex.service.Impl.observations.rinex.rinexImpl;

import rinex.dto.EpochDTO;
import rinex.model.observations.ReceiverDataModel;
import rinex.model.rinex.Observations;
import rinex.service.Impl.observations.rinex.rinexImpl.header.TypesOfObs;
import rinex.service.State;

import java.io.BufferedReader;
import java.util.*;

class ReadRinexObservationsV211Impl extends AbstractReadRinexObservations implements State {

    public ReadRinexObservationsV211Impl(ReceiverDataModel dataModel) {
        model = dataModel;
    }

    @Override
    protected EpochDTO readEpoch(BufferedReader reader) throws Exception {
        TypesOfObs types = model.getTypesOfObs();

        EpochDTO epochDto = new EpochDTO(types);
        Map<String, List<String>> obs = new LinkedHashMap<>();
        try {
            String timeData = line.substring(0,32);
            String satData = line.substring(32, line.length());

            List<String> time = splitDataBySpace(timeData);
            List<String> sat = splitSatDataHeader(satData);

            Integer obsFlag = Integer.parseInt(time.get(time.size() - 1));
            //Last number is number of satellites
            Integer numSv = Integer.parseInt(time.get(time.size() - 1));

            if (numSv > 9) {
                line = reader.readLine();
                if (line == null) {
                    throw new Exception();
                } else {
                    sat.addAll(splitSatDataHeader(line));
                }
            }

            if (sat.size() != numSv) {
                throw new Exception();
            }

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
                EpochDTO epochDto = readEpoch(reader);

                Map<TypesOfObs.Type, Observations> allObs = data.getObs();

                for (TypesOfObs.Type type : data.getTypesOfObs().getObsTypes()) {
                    Observations obs;
                    if (allObs.get(type) == null) {
                        obs = new Observations(type);
                        allObs.put(type, obs);
                    } else {
                        obs = allObs.get(type);
                    }
                    obs.add(epochDto.getObservations(type));
                }
            }
        }
    }
}
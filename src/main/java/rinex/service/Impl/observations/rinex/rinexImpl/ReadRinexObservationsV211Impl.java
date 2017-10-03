package rinex.service.Impl.observations.rinex.rinexImpl;

import com.google.common.base.Splitter;
import rinex.dto.EpochDTO;
import rinex.model.observations.ReceiverDataModel;
import rinex.service.State;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

class ReadRinexObservationsV211Impl implements State {

    String line;
    ReceiverDataModel model;

    public ReadRinexObservationsV211Impl(ReceiverDataModel dataModel) {
        model = dataModel;
    }

    public List<String> splitSatDataHeader (String line) {
        List<String> sv = Splitter.fixedLength(3).splitToList(line.trim());
        return sv;
    }

    public List<String> splitDataBySpace (String line) {
        String[] splitedLine = line.trim().replace("\\s{2,20}", "\\s").split("\\s");
        //Copy only year,month,day,hour,min,epoch_flag
        return  Arrays.stream( splitedLine).
                filter(element -> !element.isEmpty()).
                collect(Collectors.toCollection(LinkedList::new));
    }

    private EpochDTO readEpoch(BufferedReader reader) throws Exception {
        EpochDTO epochDTO = new EpochDTO(model.getTypesOfObserv());
        Map<String, List<String>> obs = new LinkedHashMap<>();
        try {
            String timeData = line.substring(0,32);
            String satData = line.substring(32, line.length());

            List<String> time = splitDataBySpace(timeData);
            List<String> sat = splitSatDataHeader(satData);

            Integer obsFlag = Integer.parseInt(time.get(time.size() - 1));
            //Last number is number of satellites
            Integer numSv = Integer.parseInt(time.get(time.size()));

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
            epochDTO.setTime(time);
            epochDTO.setFlag(obsFlag);
            epochDTO.setNumSv(numSv);
//            epochDTO.setObs(obs);
            epochDTO.parseRawData();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return epochDTO;
    }

    @Override
    public void read(BufferedReader reader, ReceiverDataModel data) throws Exception {
        while ((line = reader.readLine()) != null) {
            if (!line.contains("COMMENT")) {
                EpochDTO epochDto = readEpoch(reader);

//                    Integer numSv = Integer.parseInt(metaData.get(7).replace());
//                    if (numSv > 9) {
//                        line = line + reader.readLine();
//                    }


//                    int year = Integer.parseInt(metaData[0]);
//                    int month = Integer.parseInt(metaData[1]);
//                    int day = Integer.parseInt(metaData[2]);
//                    int hour = Integer.parseInt(metaData[3]);
//                    int min = Integer.parseInt(metaData[4]);
//                    double sec = Double.parseDouble(metaData[5]);
//                    int sv = Integer.parseInt(metaData[6]);
//
//                    double epoch = (hour*3600.0 + min*60.0 + sec);
//
//                    StringBuilder allSv = new StringBuilder();
//                    for (int i = 7; i < metaData.size(); i++) {
//                        allSv.append(metaData[i].replaceAll(" ",""));
//                    }
            }
        }
    }
}
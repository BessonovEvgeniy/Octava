package service.Impl.observations.rinex.rinexImpl;

import com.google.common.base.Splitter;
import model.observations.ReceiverDataModel;
import service.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

class ReadRinexObservationsV211Impl implements State {

    String line;
    @Override
    public void read(BufferedReader reader, ReceiverDataModel data) throws IOException {
        if (reader == null) {
            return;
        }
        while ((line = reader.readLine()) != null) {
            if (!line.contains("COMMENT")) {
                try {
                    Integer numSv = Integer.parseInt(line.substring(31,32));
                    if (numSv > 9) {
                        line = line + reader.readLine();
                    }

                    String[] metaData = line.trim().split(" ?");
                    int year = Integer.parseInt(metaData[0]);
                    int month = Integer.parseInt(metaData[1]);
                    int day = Integer.parseInt(metaData[2]);
                    int hour = Integer.parseInt(metaData[3]);
                    int min = Integer.parseInt(metaData[4]);
                    double sec = Double.parseDouble(metaData[5]);
                    int sv = Integer.parseInt(metaData[6]);

                    double epoch = (hour*3600.0 + min*60.0 + sec);

                    StringBuilder allSv = new StringBuilder();
                    for (int i = 7; i < metaData.length; i++) {
                        allSv.append(metaData[i].replaceAll(" ",""));
                    }
                    for (String item : Splitter.onPattern("\\w\\d{1,2}").split(allSv.toString())) {
                        String satSystem = item.substring(0);
                        Integer sat = Integer.parseInt(item.substring(1,item.length()));
                        line = reader.readLine();

                    }

                    data.getEpoch().add(epoch);
                } catch (NumberFormatException e) {
                    continue; //Skip this line and read next line
                }
            }
        }
    }
}
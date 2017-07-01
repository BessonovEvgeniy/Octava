package service.observations.rinex.rinexImpl;

import com.google.common.base.Splitter;
import model.observations.TNP;
import service.observations.rinex.State;

import java.io.BufferedReader;
import java.io.IOException;

class ReadRinexObservationsV211Impl implements State {

    String line;
    @Override
    public void read(BufferedReader reader, TNP tnp) throws IOException {
        if (reader == null) {
            return;
        }
        while ((line = reader.readLine()) != null) {
            if (!line.contains("COMMENT")) {
                Integer numSv = Integer.parseInt(line.substring(31,32));
                if (numSv > 9) {
                    line = line + reader.readLine();
                }
                String[] metaData = line.trim().split(" ?");
                Integer year = Integer.parseInt(metaData[0]);
                Integer month = Integer.parseInt(metaData[1]);
                Integer day = Integer.parseInt(metaData[2]);
                Integer hour = Integer.parseInt(metaData[3]);
                Integer min = Integer.parseInt(metaData[4]);
                Double sec = Double.parseDouble(metaData[5]);
                Integer sv = Integer.parseInt(metaData[6]);

                tnp.addEpoch(hour*3600.0 + min*60.0 + sec);

                StringBuilder allSv = new StringBuilder();
                for (int i = 7; i < metaData.length; i++) {
                    allSv.append(metaData[i].replaceAll(" ",""));
                }
                for (String item : Splitter.onPattern("\\w\\d{1,2}").split(allSv.toString())) {
                    String satSystem = item.substring(0);
                    Integer sat = Integer.parseInt(item.substring(1,item.length()));
                    line = reader.readLine();

                }
            }
        }
    }
}
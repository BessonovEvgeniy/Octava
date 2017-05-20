package service.observations.rinex;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import model.observations.TNP;
import model.rinex.Header;
import model.rinex.Observations;
import service.observations.rinex.headerLabels.HeaderLabel;
import service.observations.rinex.headerLabels.RinexVersionType;

import javax.validation.constraints.Size;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RinexService {

    private StringBuilder line = new StringBuilder();
    private String title;
    private BufferedReader reader;
    private State state;
    private TNP tnp = new TNP();

    RinexService () {
        state = new ReadHeader();
    }

    public TNP readRinex(@NonNull @Size(min = 1) String fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
            state.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return tnp;
        }
    }

    private class ReadHeader implements State {

        Header header = new Header();

        HeaderLabel headerLabel;
        String line;
        HeaderLabelFactory headerLabelFactory = new HeaderLabelFactory();

        @Override
        public void read() throws IOException{

            while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")){
//              Skip comments and non existing Titles
                if (line.contains("COMMENT") || (headerLabel = headerLabelFactory.getHeaderLabel(line)) == null) {
                    continue;
                }
                header.add(headerLabel);
            }
        }
    }
    interface State {
        void read() throws IOException;
    }

    public static void main(String[] args) {
        String filename = "d:\\3 GPS data\\2013\\010\\RINEX\\ALCI0100.13O";
        try {
            RinexService rinexService = new RinexService();
            rinexService.readRinex(filename);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
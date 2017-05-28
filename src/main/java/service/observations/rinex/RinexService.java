package service.observations.rinex;

import lombok.NonNull;
import model.observations.TNP;
import model.rinex.Header;
import org.springframework.stereotype.Service;
import service.observations.rinex.headerLabels.HeaderLabel;

import javax.validation.constraints.Size;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
public class RinexService {

    private StringBuilder line = new StringBuilder();
    private String title;
    private BufferedReader reader;
    private State state;
    private TNP tnp = new TNP();

    public TNP readRinex(@NonNull @Size(min = 1) String fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
            changeState(new ReadHeader());
            state.read();
//            changeState(new ReadObseravtions());
//            state.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return tnp;
        }
    }

    private void changeState(State state){
        this.state = state;
    }

    private class ReadHeader implements State {

        Header header = new Header();

        HeaderLabel headerLabel;
        String line;
        HeaderLabelFactory headerLabelFactory = new HeaderLabelFactory();

        @Override
        public void read() throws IOException{
            if (reader == null) {
                return;
            }
            while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")) {
//             Skip comments and non existing Titles
                if (line.contains("COMMENT")) {
                    continue;
                }
                header.set(headerLabelFactory.getHeaderLabel(line));
            }
        }
    }
    interface State {
        void read() throws IOException;
    }

    public static void main(String[] args) {
//        String filename = "d:\\3 GPS data\\2013\\010\\RINEX\\ALCI0100.13O";
        String filename = "//home//ionex//1 IdeaProjects//Octava//X07100.17o";
        try {
            RinexService rinexService = new RinexService();
            rinexService.readRinex(filename);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
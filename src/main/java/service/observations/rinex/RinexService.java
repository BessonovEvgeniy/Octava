package service.observations.rinex;

import model.observations.TNP;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class RinexService {

    private State state;

    private void changeState(State state) {
        this.state = state;
    }

    public TNP readRinex(InputStream uploadedInputStream) {
        TNP tnp = new TNP();
        try {
            changeState(new ReadHeader());
            boolean isHeaderRead = state.read(uploadedInputStream, tnp);
            boolean isObservationRead = state.read(uploadedInputStream, tnp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidRinexDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return tnp;
        }
    }

    public static void main(String[] args) {
//        String filename = "d:\\3 GPS data\\2013\\010\\RINEX\\ALCI0100.13O";
        String filename = "//home//ionex//1 IdeaProjects//Octava//X07100.17o";
        try {
            InputStream inputStream = new FileInputStream(filename);
            RinexService rinexService = new RinexService();
            rinexService.readRinex(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
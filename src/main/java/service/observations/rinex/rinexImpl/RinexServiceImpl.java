package service.observations.rinex.rinexImpl;

import lombok.NonNull;
import model.observations.TNP;
import org.springframework.stereotype.Service;

import service.observations.rinex.RinexService;
import service.observations.rinex.State;

import javax.validation.constraints.Size;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
public class RinexServiceImpl implements RinexService {

    private BufferedReader reader;
    private State state;
    private TNP tnp = new TNP();

    public TNP readRinex(@NonNull @Size(min = 1) String fileName) {
        try {
            reader = new BufferedReader(new FileReader(fileName));
            changeState(new ReadHeaderImpl());
            state.read(reader, tnp);
            changeState(new ReadObservationsImpl());
            state.read(reader, tnp);
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

    public static void main(String[] args) {
        String filename = "d:\\3 GPS data\\2013\\010\\RINEX\\ALCI0100.13O";
//        String filename = "//home//ionex//1 IdeaProjects//Octava//X07100.17o";
        try {
            RinexServiceImpl rinexService = new RinexServiceImpl();
            rinexService.readRinex(filename);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
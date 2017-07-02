package controller;

import model.observations.ReceiverDataModel;
import service.Impl.observations.rinex.rinexImpl.RinexServiceImpl;

public class ReciverDataController {




    public static void main(String[] args) {
        String filename = "d:\\3 GPS data\\2013\\010\\RINEX\\ALCI0100.13O";
//        String filename = "//home//ionex//1 IdeaProjects//Octava//X07100.17o";
        try {
            RinexServiceImpl rinexService = new RinexServiceImpl();
            ReceiverDataModel data = rinexService.readRinex(filename);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package model.rinex;


import lombok.Data;
import service.observations.rinex.Proccess;
import service.observations.rinex.rinexImpl.header.MarkerName;
import service.observations.rinex.rinexImpl.header.ObserverAgency;
import service.observations.rinex.rinexImpl.header.PgmRunByDate;
import service.observations.rinex.rinexImpl.header.RinexVersionType;

import java.util.ArrayList;
import java.util.List;

public @Data class Header  {
    private Proccess rinexVersionType = new RinexVersionType();
    private Proccess pgmRunByDate = new PgmRunByDate();
    private Proccess markerName = new MarkerName();
    private Proccess observerAgency = new ObserverAgency();

    private List<Proccess> proccesses = new ArrayList<>();

    public void set(Proccess proccess){
        if (proccess instanceof RinexVersionType) {
            rinexVersionType = proccess;
        }
        else if (proccess instanceof PgmRunByDate) {
            pgmRunByDate = proccess;
        }
        else if (proccess instanceof MarkerName) {
            markerName = proccess;
        }
        else if (proccess instanceof ObserverAgency) {
            observerAgency = proccess;
        }
    }

}
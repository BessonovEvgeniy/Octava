package service.observations.rinex.rinexImpl;

import com.google.common.base.Splitter;
import model.observations.TNP;
import model.rinex.Header;
import service.observations.rinex.State;
import service.observations.rinex.rinexImpl.header.RinexVersionType;

import java.io.BufferedReader;
import java.io.IOException;

class ReadRinexObservationsDecorator implements State {

    String line;
    @Override
    public void read(BufferedReader reader, TNP tnp) throws IOException {
        Header header = tnp.getHeader();
        if (header != null) {
            String version = ((RinexVersionType) header.getRinexVersionType()).getVersion();
            if (version != null) {
                State rinexObservationReader = ReadRinexObservationsFactory.rinexObservationReaders.get(version);
                if (rinexObservationReader != null) {
                    rinexObservationReader.read(reader, tnp);
                }
            }
        }
    }
}
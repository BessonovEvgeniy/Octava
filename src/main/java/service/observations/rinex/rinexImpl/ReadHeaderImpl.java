package service.observations.rinex.rinexImpl;

import model.observations.TNP;
import model.rinex.Header;
import service.observations.rinex.Proccess;
import service.observations.rinex.State;
import service.observations.rinex.rinexImpl.header.HeaderLabelFactory;

import java.io.BufferedReader;
import java.io.IOException;

class ReadHeaderImpl implements State {

    HeaderLabelFactory headerLabelFactory = new HeaderLabelFactory();
    Header header = new Header();

    @Override
    public void read(BufferedReader reader, TNP tnp) throws IOException {
        String line;

        if (reader == null) {
            return;
        }
        while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")) {
            if (!line.contains("COMMENT")) {
                Proccess headerLabel = headerLabelFactory.getHeaderLabel(line);
                if (headerLabel != null) {
                    headerLabel.parse(line);
                }
            }
        }
    }
}
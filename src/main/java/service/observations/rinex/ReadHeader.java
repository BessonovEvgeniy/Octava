package service.observations.rinex;

import model.observations.TNP;
import model.rinex.Header;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class ReadHeader implements State {

    @Override
    public boolean read(InputStream uploadedInputStream, TNP tnp) throws IOException, InvalidRinexDataException {
        boolean resault = false;

        if (uploadedInputStream == null) {
            return resault;
        }
        HeaderLabelFactory headerLabelFactory = new HeaderLabelFactory();
        Header header = tnp.getHeader();

        BufferedReader reader = new BufferedReader(new InputStreamReader(uploadedInputStream));

        String line;

        while ((line = reader.readLine()) !=null && !line.contains("END OF HEADER")){
//             Skip comments and non existing Titles
            if (line.contains("COMMENT")) {
                continue;
            }
            header.set(headerLabelFactory.getHeaderLabel(line));
        }

        if (header instanceof Header.HeaderNull) {
            return false;
        }

        return true;
    }
}
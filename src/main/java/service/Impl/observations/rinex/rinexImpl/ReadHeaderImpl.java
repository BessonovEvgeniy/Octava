package service.Impl.observations.rinex.rinexImpl;

import model.observations.ReceiverDataModel;
import model.rinex.Header;
import service.HeaderLabel;
import service.State;
import service.Impl.observations.rinex.rinexImpl.header.HeaderLabelFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;

class ReadHeaderImpl implements State {

    HeaderLabelFactory headerLabelFactory = new HeaderLabelFactory();
    Header header = new Header();

    @Override
    public void read(BufferedReader reader, ReceiverDataModel data) throws IOException {
        String line;

        if (reader == null) {
            return;
        }
        while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")) {
            if (!line.contains("COMMENT")) {
                HeaderLabel headerLabel = headerLabelFactory.getHeaderLabel(line);
                if (headerLabel != null) {
                    headerLabel.parse(line);
                }
            }
        }
    }

    private boolean setHeaderLabel(HeaderLabel headerLabel, ReceiverDataModel data) throws IllegalAccessException {
        Field[] fields = data.getClass().getFields();
        for (Field field : fields) {
            if (field.getType().getName().equals(headerLabel.getClass().getName())) {
                field.set(data, headerLabel);
            }
        }
        return true;
    }
}
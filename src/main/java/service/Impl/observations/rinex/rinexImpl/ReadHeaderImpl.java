package service.Impl.observations.rinex.rinexImpl;

import model.observations.ReceiverDataModel;
import service.HeaderLabel;
import service.Impl.observations.rinex.rinexImpl.header.HeaderLabelFactory;
import service.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;

class ReadHeaderImpl implements State {

    HeaderLabelFactory headerLabelFactory = new HeaderLabelFactory();

    @Override
    public void read(BufferedReader reader, ReceiverDataModel data) throws IOException, IllegalAccessException {
        String line;

        if (reader == null) {
            return;
        }
        while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")) {
            if (!line.contains("COMMENT")) {
                HeaderLabel headerLabel = headerLabelFactory.getHeaderLabel(line);
                if (headerLabel != null) {
                    if (headerLabel.parse(line)) {
                        setHeaderLabel(headerLabel, data);
                    }
                }
            }
        }
    }

    private boolean setHeaderLabel(HeaderLabel headerLabel, ReceiverDataModel data) throws IllegalAccessException {
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().getName().equals(headerLabel.getClass().getName())) {
                field.setAccessible(true);
                field.set(data, headerLabel);
                field.setAccessible(false);
                break;
            }
        }
        return true;
    }
}
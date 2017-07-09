package service.Impl.observations.rinex.rinexImpl;

import model.observations.ReceiverDataModel;
import service.HeaderLabel;
import service.Impl.observations.rinex.rinexImpl.header.HeaderLabelFactory;
import service.State;

import java.io.BufferedReader;
import java.lang.reflect.Field;

class ReadHeaderImpl implements State {

    HeaderLabelFactory headerLabelFactory = new HeaderLabelFactory();

    @Override
    public void read(BufferedReader reader, ReceiverDataModel data) throws Exception {
        if (reader != null) {
            String line;
            while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")) {
                HeaderLabel headerLabel = headerLabelFactory.getHeaderLabel(line);
                setHeaderLabel(headerLabel, data);
            }
        }
    }

    private boolean setHeaderLabel(HeaderLabel headerLabel, ReceiverDataModel data) throws IllegalAccessException {
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().getName().equals(headerLabel.getClass().getName())) {
                field.setAccessible(true);
                field.set(data, headerLabel);
                break;
            }
        }
        return true;
    }
}
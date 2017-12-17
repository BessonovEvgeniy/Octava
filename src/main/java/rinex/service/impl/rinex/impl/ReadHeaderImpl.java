package rinex.service.impl.rinex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rinex.model.observations.ReceiverDataModel;
import rinex.model.observations.header.HeaderLabel;
import rinex.service.State;

import java.io.BufferedReader;
import java.lang.reflect.Field;

@Service
class ReadHeaderImpl <T extends HeaderLabel> implements State {

    @Autowired
    private HeaderLabelFactory headerLabelFactory;

    @Override
    public void read(BufferedReader reader, ReceiverDataModel data) throws Exception {
        if (reader == null) {
            return;
        }
        String line;
        while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")) {
            HeaderLabel headerLabel = headerLabelFactory.getHeaderLabel(line);
            setHeaderLabel((T) headerLabel, data);
        }
    }

    private void setHeaderLabel(T headerLabel, ReceiverDataModel data) throws IllegalAccessException {
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isInstance(headerLabel)) {
                field.setAccessible(true);
                field.set(data, headerLabel);
                break;
            }
        }
    }
}
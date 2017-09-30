package rinex.service.Impl.observations.rinex.rinexImpl;

import rinex.model.observations.ReceiverDataModel;
import rinex.service.HeaderLabel;
import rinex.service.Impl.observations.rinex.rinexImpl.header.HeaderLabelFactory;
import rinex.service.State;

import java.io.BufferedReader;
import java.lang.reflect.Field;

class ReadHeaderImpl <T extends HeaderLabel> implements State {

    HeaderLabelFactory headerLabelFactory = new HeaderLabelFactory();

    @Override
    public void read(BufferedReader reader, ReceiverDataModel data) throws Exception {
        if (reader != null) {
            String line;
            while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")) {
                HeaderLabel headerLabel = headerLabelFactory.getHeaderLabel(line);
                if (headerLabel != null) {
                    setHeaderLabel((T) headerLabel, data);
                }
            }
        }
    }

    private Boolean setHeaderLabel(T headerLabel, ReceiverDataModel data) throws IllegalAccessException {
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isInstance(headerLabel)) {
                field.setAccessible(true);
                field.set(data, headerLabel);
                break;
            }
        }
        return true;
    }
}
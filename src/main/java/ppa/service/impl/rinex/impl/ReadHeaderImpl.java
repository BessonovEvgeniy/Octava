package ppa.service.impl.rinex.impl;

import config.injector.InjectLog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppa.model.observation.ReceiverDataModel;
import ppa.model.observation.header.HeaderLabel;
import ppa.service.RinexReader;

import java.io.BufferedReader;
import java.lang.reflect.Field;

@Service
public class ReadHeaderImpl <T extends HeaderLabel> implements RinexReader {

    @InjectLog
    private Logger log;

    @Autowired
    private HeaderLabelFactory headerLabelFactory;

    @Override
    public void read(BufferedReader reader, ReceiverDataModel data) throws Exception {
        if (reader == null) {
            return;
        }
        String line;
        log.info("Header reading...");
        while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")) {
            HeaderLabel headerLabel = headerLabelFactory.getHeaderLabel(line);
            if (headerLabel != null) {
                setHeaderLabel((T) headerLabel, data);
            }
        }
        log.info("Done.");
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
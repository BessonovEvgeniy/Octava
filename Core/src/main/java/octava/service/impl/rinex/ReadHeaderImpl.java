package octava.service.impl.rinex;

import octava.model.observation.ReceiverDataModel;
import octava.model.observation.header.HeaderLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import octava.service.RinexSectionReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;

@Service
public class ReadHeaderImpl <T extends HeaderLabel> implements RinexSectionReader {

    private static final Logger LOG = LoggerFactory.getLogger(ReadHeaderImpl.class);

    @Autowired
    private HeaderLabelFactory headerLabelFactory;

    @Override
    public void read(BufferedReader reader, ReceiverDataModel data){
        if (reader == null) {
            return;
        }
        String line;
        LOG.info("Header reading...");
        try {
            while ((line = reader.readLine()) != null && !line.contains("END OF HEADER")) {
                HeaderLabel headerLabel = headerLabelFactory.getHeaderLabel(line);
                if (headerLabel != null) {
                    setHeaderLabel((T) headerLabel, data);
                }
            }
        } catch (IOException e) {
            LOG.error("Rinex file corrupted");
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            LOG.error("Error rinex file data population");
            e.printStackTrace();
            throw new RuntimeException();
        }
        LOG.info("Done.");
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
package octava.service.impl.observations.processors;


import octava.model.observation.ReceiverDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import octava.service.RinexReader;
import octava.service.impl.rinex.ReadHeaderImpl;
import octava.service.impl.rinex.ReadRinexObservationsDecorator;

import javax.inject.Provider;
import java.io.*;
import java.nio.file.Path;

@Component
public class DefaultRinexReader implements RinexReader {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRinexReader.class);

    @Autowired
    private ReadHeaderImpl readHeader;

    @Autowired
    private ReadRinexObservationsDecorator readObservations;

    public ReceiverDataModel read(InputStream inputStream) {
        if (inputStream == null) {
            return ReceiverDataModel.NULL;
        } else {
            final ReceiverDataModel data = new ReceiverDataModel();

            LOG.info("Rinex reading...");
            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(inputStream))) {

                readHeader.read(reader, data);
                readObservations.read(reader, data);
            } catch (Exception e) {
                LOG.error("Can't read rinex.");
                e.printStackTrace();
                return ReceiverDataModel.NULL;
            }
            return data;
        }
    }

    @Override
    public ReceiverDataModel read(final File file) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            LOG.error("Invalid file " + file.getAbsoluteFile());
            e.printStackTrace();
            return ReceiverDataModel.NULL;
        }
        return read(inputStream);
    }
}
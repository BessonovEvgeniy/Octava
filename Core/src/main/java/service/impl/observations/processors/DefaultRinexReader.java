package service.impl.observations.processors;


import model.observation.ReceiverDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.RinexReader;
import service.impl.rinex.ReadHeaderImpl;
import service.impl.rinex.ReadRinexObservationsDecorator;

import javax.inject.Provider;
import java.io.*;

@Component
public class DefaultRinexReader implements RinexReader {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRinexReader.class);

    @Autowired
    private Provider<ReceiverDataModel> rdmProvider;

    @Autowired
    private ReadHeaderImpl readHeader;

    @Autowired
    private ReadRinexObservationsDecorator readObservations;

    public ReceiverDataModel read(InputStream inputStream) {
        if (inputStream == null) {
            return ReceiverDataModel.NULL;
        } else {
            ReceiverDataModel data = rdmProvider.get();

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

    public ReceiverDataModel read(File file) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            LOG.error("Invalid file " + file.getPath());
            e.printStackTrace();
            return ReceiverDataModel.NULL;
        }
        return read(inputStream);
    }
}
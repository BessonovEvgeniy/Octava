package octava.service.impl.observations.processors;


import octava.model.observation.ReceiverDataModel;
import octava.model.rinex.RinexFileMediaModel;
import octava.service.RinexReaderService;
import octava.service.impl.rinex.ReadHeaderImpl;
import octava.service.impl.rinex.ReadRinexObservationsDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service("rinexReaderService")
public class RinexReaderServiceImpl implements RinexReaderService {

    private static final Logger LOG = LoggerFactory.getLogger(RinexReaderServiceImpl.class);

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
                LOG.error("Can't read rinex.", e);
                e.printStackTrace();
                return ReceiverDataModel.NULL;
            }
            return data;
        }
    }

    @Override
    public ReceiverDataModel read(final File file) {
        ReceiverDataModel receiverDataModel = ReceiverDataModel.NULL;
        try (InputStream inputStream = new FileInputStream(file)) {
            receiverDataModel = read(inputStream);
        } catch (Exception e) {
            LOG.error("Invalid file " + file.getAbsoluteFile(), e);
        }
        return receiverDataModel;
    }

    @Override
    public ReceiverDataModel read(RinexFileMediaModel file) {
        final String fileName = file.getFullName();

        return read(new File(fileName));
    }
}
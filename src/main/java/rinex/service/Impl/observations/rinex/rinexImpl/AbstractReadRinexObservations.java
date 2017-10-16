package rinex.service.Impl.observations.rinex.rinexImpl;

import com.google.common.base.Splitter;
import rinex.dto.EpochDto;
import rinex.model.observations.ReceiverDataModel;
import rinex.service.State;

import java.io.BufferedReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

abstract class AbstractReadRinexObservations implements State {

    String line;
    ReceiverDataModel model;
    Pattern satelliteSplterator = Pattern.compile("");

    List<String> splitSatDataHeader(String line) {
        return new LinkedList<>(Splitter.fixedLength(3).splitToList(line.trim()));
    }

    List<String> splitDataBySpace(String line) {
        String[] splitedLine = line.trim().replace("\\s{2,20}", "\\s").split("\\s");
        //Copy only year,month,day,hour,min,epoch_flag
        return  Arrays.stream( splitedLine).
                filter(element -> !element.isEmpty()).
                collect(Collectors.toCollection(LinkedList::new));
    }

    public List<String> splitSatData (String line) {

        String[] splitedLine = line.trim().replace("\\s{2,20}", "\\s").split("\\s");
        //Copy only year,month,day,hour,min,epoch_flag
        return  Arrays.stream( splitedLine).
                filter(element -> !element.isEmpty()).
                collect(Collectors.toCollection(LinkedList::new));
    }

    protected abstract EpochDto readEpoch(BufferedReader reader) throws Exception;

    @Override
    public abstract void read(BufferedReader reader, ReceiverDataModel data) throws Exception;
}
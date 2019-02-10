package service.impl.observations.header;

import model.observation.header.impl.OfSatellites;
import org.springframework.stereotype.Service;
import service.HeaderLabelParserService;

import java.util.regex.Pattern;

@Service("PRN / # OF OBS")
public class PrnVsOfObs implements HeaderLabelParserService<OfSatellites> {

    public final static Pattern PATTERN = Pattern.compile("(.{1,60})PRN / # OF OBS");

    @Override
    public OfSatellites parse(String line) {
        return null;//skip this data
    }
}

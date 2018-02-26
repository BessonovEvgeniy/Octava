package ppa.service.impl.observations.header.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.OfSatellites;
import ppa.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Pattern;

@Service("PRN / # OF OBS")
public @Data class PrnVsOfObs implements HeaderLabelParserService<OfSatellites> {

    private Pattern pattern = Pattern.compile("(.{1,60})PRN / # OF OBS");

    @Override
    public OfSatellites parse(String line) {
        return null;//skip this data
    }
}
package ppa.service.impl.observations.header.impl;

import lombok.Data;
import ppa.model.observation.header.impl.RecTypeVers;
import ppa.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public @Data class RecTypeVersParserServiceImpl implements HeaderLabelParserService<RecTypeVers> {

    private Pattern pattern = Pattern.compile("\\s{5}(\\d{1,9}\\.\\d{1,2})\\s{4,11}[\\w,\\s]{20}([M,G,E,R,S]).{19}RINEX VERSION / TYPE");

    @Override
    public RecTypeVers parse(String line) {

        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String rec = matcher.group(1).trim();
            String type = matcher.group(2).trim();
            String version = matcher.group(3).trim();
            return new RecTypeVers(rec, type, version);
        }
        return RecTypeVers.NULL;
    }
}


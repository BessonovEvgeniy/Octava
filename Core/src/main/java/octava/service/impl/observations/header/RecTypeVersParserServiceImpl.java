package octava.service.impl.observations.header;

import octava.model.observation.header.impl.RecTypeVersModel;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RecTypeVersParserServiceImpl implements HeaderLabelParserService<RecTypeVersModel> {

    public final static Pattern PATTERN = Pattern.compile("\\s{5}(\\d{1,9}\\.\\d{1,2})\\s{4,11}[\\w,\\s]{20}([M,G,E,R,S]).{19}RINEX VERSION / TYPE");

    @Override
    public RecTypeVersModel parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            String rec = matcher.group(1).trim();
            String type = matcher.group(2).trim();
            String version = matcher.group(3).trim();
            return new RecTypeVersModel(rec, type, version);
        }
        return RecTypeVersModel.NULL;
    }
}


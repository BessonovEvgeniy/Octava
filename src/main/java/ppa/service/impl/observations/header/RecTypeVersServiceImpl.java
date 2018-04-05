package ppa.service.impl.observations.header;

import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.RecTypeVers;
import ppa.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("REC # / TYPE / VERS")
public class RecTypeVersServiceImpl implements HeaderLabelParserService<RecTypeVers> {

    public final static Pattern PATTERN = Pattern.compile("(.{20})(.{20})(.{20})REC # / TYPE / VERS ");

    @Override
    public RecTypeVers parse(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            String rec = matcher.group(1);
            String type = matcher.group(2);
            String ver = matcher.group(3);
            return new RecTypeVers(rec, type, ver);
        }
        return RecTypeVers.NULL;
    }
}

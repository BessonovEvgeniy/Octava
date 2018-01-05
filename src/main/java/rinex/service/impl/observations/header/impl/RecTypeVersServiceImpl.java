package rinex.service.impl.observations.header.impl;

import lombok.Data;
import org.springframework.stereotype.Service;
import rinex.model.observation.header.impl.RecTypeVers;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("REC # / TYPE / VERS")
public @Data class RecTypeVersServiceImpl implements HeaderLabelParserService<RecTypeVers> {

    private Pattern pattern = Pattern.compile("(.{20})(.{20})(.{20})REC # / TYPE / VERS ");

    @Override
    public RecTypeVers parse(String line) {
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            String rec = matcher.group(1);
            String type = matcher.group(2);
            String ver = matcher.group(3);
            return new RecTypeVers(rec, type, ver);
        }
        return RecTypeVers.NULL;
    }
}

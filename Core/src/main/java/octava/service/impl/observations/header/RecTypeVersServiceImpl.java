package octava.service.impl.observations.header;

import octava.model.observation.header.impl.RecTypeVersModel;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("REC # / TYPE / VERS")
public class RecTypeVersServiceImpl implements HeaderLabelParserService<RecTypeVersModel> {

    public final static Pattern PATTERN = Pattern.compile("(.{20})(.{20})(.{20})REC # / TYPE / VERS ");

    @Override
    public RecTypeVersModel parse(final String line) {
        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final String rec = matcher.group(1);
            final String type = matcher.group(2);
            final String ver = matcher.group(3);
            return new RecTypeVersModel(rec, type, ver);
        }
        return RecTypeVersModel.NULL;
    }
}

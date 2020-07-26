package octava.service.impl.observations.header;

import octava.model.observation.header.impl.ObserverAgencyModel;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("OBSERVER / AGENCY")
public class ObserverAgencyParserServiceImpl implements HeaderLabelParserService<ObserverAgencyModel> {

    public final static Pattern PATTERN = Pattern.compile("(.{20})(.{40})OBSERVER / AGENCY   ");

    @Override
    public ObserverAgencyModel parse(final String line) {
        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final String observerName = matcher.group(1).trim();
            final String agencyName = matcher.group(2).trim();
            return new ObserverAgencyModel(observerName, agencyName);
        }
        return ObserverAgencyModel.NULL;
    }
}

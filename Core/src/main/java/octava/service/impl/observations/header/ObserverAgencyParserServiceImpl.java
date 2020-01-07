package octava.service.impl.observations.header;

import octava.model.observation.header.impl.ObserverAgency;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("OBSERVER / AGENCY")
public class ObserverAgencyParserServiceImpl implements HeaderLabelParserService<ObserverAgency> {

    public final static Pattern PATTERN = Pattern.compile("(.{20})(.{40})OBSERVER / AGENCY   ");

    @Override
    public ObserverAgency parse(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            String observerName = matcher.group(1).trim();
            String agencyName = matcher.group(2).trim();
            return new ObserverAgency(observerName, agencyName);
        }
        return ObserverAgency.NULL;
    }
}
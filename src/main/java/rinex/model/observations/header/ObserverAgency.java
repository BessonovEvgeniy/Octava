package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("OBSERVER / AGENCY")
public @Data class ObserverAgency implements HeaderLabel {

    private String observerName;
    private String agencyName;
    private Pattern pattern = Pattern.compile("(.{20})(.{40})OBSERVER / AGENCY   ");

    @Override
    public boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);
        boolean find = matcher.find();
        if (find) {
            observerName = matcher.group(1).trim();
            agencyName = matcher.group(2).trim();
        }
        return find;
    }
}

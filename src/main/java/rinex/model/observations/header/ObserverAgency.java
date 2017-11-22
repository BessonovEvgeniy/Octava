package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("OBSERVER / AGENCY")
public @Data class ObserverAgency implements HeaderLabel {

    private String observerName;
    private String agencyName;
    private Pattern pattern = Pattern.compile("(.{20})(.{40})(OBSERVER / AGENCY)");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseTwoParams(pattern, line);
        if (find) {
            List<String> params = parser.getParams();
            observerName = params.get(0);
            agencyName = params.get(1);
        }
        return find;
    }
}

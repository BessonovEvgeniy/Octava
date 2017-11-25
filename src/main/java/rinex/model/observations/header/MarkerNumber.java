package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("MARKER NUMBER")
public @Data class MarkerNumber implements HeaderLabel {

    private String markerNumber;

    private Pattern pattern = Pattern.compile(".{1,60}MARKER NUMBER");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseOneParam(pattern, line);
        if (find) {
            List<String> params = parser.getParams();
            markerNumber = params.get(0);
        }
        return find;
    }
}

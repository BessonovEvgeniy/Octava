package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("MARKER NAME")
public @Data class MarkerName implements HeaderLabel {

    private String markerName;

    private Pattern pattern = Pattern.compile("(.{1,60})MARKER NAME");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseOneParam(pattern, line);
        if (find) {
            List<String> params = parser.getParams();
            markerName = params.get(0);
        }
        return find;
    }
}

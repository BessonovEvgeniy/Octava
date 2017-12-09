package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("REC # / TYPE / VERS")
public @Data class RecTypeVers implements HeaderLabel {

    private String rec;
    private String type;
    private String vers;

    private Pattern pattern = Pattern.compile("(.{20})(.{20})(.{20})REC # / TYPE / VERS ");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseThreeParams(pattern, line);
        if (find) {
            List<String> params = parser.getParams();
            rec = params.get(0);
            type = params.get(1);
            vers = params.get(2);
        }
        return find;
    }
}

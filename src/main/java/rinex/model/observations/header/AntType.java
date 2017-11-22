package rinex.model.observations.header;

import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component("ANT # / TYPE")
public @Data class AntType implements HeaderLabel {

    private String ant;
    private String type;
    private Pattern pattern = Pattern.compile(Strings.repeat("(.{20})", 3) + "(ANT # / TYPE)");

    @Override
    public boolean parse(String line) {
        HeaderLabelParser parser = new HeaderLabelParser();

        boolean find = parser.parseTwoParams(pattern, line);
        if (find) {
            List<String> params = parser.getParams();
            ant = params.get(0);
            type = params.get(1);
        }
        return find;
    }
}

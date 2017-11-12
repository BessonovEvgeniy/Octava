package rinex.model.observations.header;

import com.google.common.base.Strings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@Component("ANT # / TYPE")
public @Data class AntType extends AbstractHeaderLabel {

    private String ant;
    private String type;

    public AntType() {
        init();
    }

    @PostConstruct
    private void init() {
        stringPattern = Strings.repeat("(.{20})", 3) + "(ANT # / TYPE)";
        pattern = Pattern.compile(stringPattern);
    }

    @Override
    public Boolean parse(String line) {

        Matcher matcher = pattern.matcher(line);
        Boolean isFind = matcher.find();
        ant = isFind ? matcher.group(1).trim() : "";
        type = isFind ? matcher.group(2).trim() : "";
        return isFind;
    }
}

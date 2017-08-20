package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class AntType extends AbstractHeaderLabel {

    private String ant;
    private String type;

    public AntType() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("(.{20})(.{20})(.{20})(ANT # / TYPE)");
    }

    @Override
    public boolean parse(String line) {

        Matcher matcher = pattern.matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            ant = matcher.group(1).trim();
            type = matcher.group(2).trim();
        }
        return isFind;
    }
}

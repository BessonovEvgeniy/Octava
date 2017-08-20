package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class RecTypeVers extends AbstractHeaderLabel {

    private String rec;
    private String type;
    private String vers;

    public RecTypeVers() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("(.{20})(.{20})(.{20})(REC # / TYPE / VERS)");
    }

    @Override
    public boolean parse(String line) {

        Matcher matcher = pattern.matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            rec = matcher.group(1).trim();
            type = matcher.group(2).trim();
            vers = matcher.group(3).trim();
        }
        return isFind;
    }
}

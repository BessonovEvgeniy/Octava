package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
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
    public Boolean parse(String line) {

        Matcher matcher = pattern.matcher(line);

        Boolean find = matcher.find();
        rec = find ? matcher.group(1).trim() : "";
        type = find ? matcher.group(2).trim() : "";
        vers = find ? matcher.group(3).trim() : "";
        return find;
    }
}

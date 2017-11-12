package rinex.model.observations.header;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@Component("OBSERVER / AGENCY")
public @Data class ObserverAgency extends AbstractHeaderLabel {

    private String observerName;
    private String agencyName;

    public ObserverAgency() {
        init();
    }

    @PostConstruct
    protected void init() {
        pattern = Pattern.compile("(.{20})(.{40})(OBSERVER / AGENCY)");
    }

    @Override
    public Boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);
        Boolean isFind = matcher.find();
        observerName = isFind ? matcher.group(1).trim() : "";
        agencyName = isFind ? matcher.group(2).trim() : "";
        return isFind;
    }
}

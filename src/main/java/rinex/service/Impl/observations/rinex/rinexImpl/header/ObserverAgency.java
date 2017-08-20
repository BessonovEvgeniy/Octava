package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
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
    public boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);
        boolean isFind = matcher.find();
        if (isFind) {
            observerName = matcher.group(1).trim();
            agencyName = matcher.group(2).trim();
        }
        return isFind;
    }
}

package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class LeapSeconds extends AbstractHeaderLabel {

    private Integer leapSeconds;

    public LeapSeconds() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("(.{1,60})LEAP SECONDS");
        leapSeconds = 0;
    }

    @Override
    public boolean parse(String line)  {
        Matcher matcher = pattern.matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            leapSeconds = Integer.parseInt(matcher.group(1).trim());
        }
        return isFind;
    }
}

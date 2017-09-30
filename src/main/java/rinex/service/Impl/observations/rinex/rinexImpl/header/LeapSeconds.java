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
    }

    @Override
    public Boolean parse(String line)  {
        Matcher matcher = pattern.matcher(line);

        Boolean isFind = matcher.find();
        leapSeconds = isFind ? Integer.parseInt(matcher.group(1).trim()) : 0;
        return isFind;
    }
}

package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("leapSeconds")
public @Data class LeapSeconds implements HeaderLabel {

    private Integer leapSeconds;

    @Override
    public boolean parse(String line)  {
        Matcher matcher = Pattern.compile("(.{1,60})LEAP SECONDS").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            leapSeconds = Integer.parseInt(matcher.group(1).trim());
        } else {
            leapSeconds = 0;
        }
        return isFind;
    }
}

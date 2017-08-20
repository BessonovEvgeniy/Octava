package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class Interval extends AbstractHeaderLabel {

    private Double interval;

    public Interval() {
        init();
    }

    @PostConstruct
    private void init() {
        interval = 0.0;
        pattern = Pattern.compile("(\\d{1,7}\\.\\d{1,4}).*INTERVAL");
    }

    @Override
    public boolean parse(String line) throws RinexHeaderException {

        Matcher matcher = pattern.matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            interval = Double.parseDouble(matcher.group(1));
        } else {
            init();
        }
        return isFind;
    }
}

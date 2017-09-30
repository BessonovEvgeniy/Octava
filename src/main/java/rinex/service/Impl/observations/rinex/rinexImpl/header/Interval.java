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
        pattern = Pattern.compile("(\\d{1,7}\\.\\d{1,4}).*INTERVAL");
    }

    @Override
    public Boolean parse(String line) throws RinexHeaderException {
        Matcher matcher = pattern.matcher(line);
        Boolean isFind = matcher.find();

        interval = isFind ? Double.parseDouble(matcher.group(1)) : 0.0;
        return isFind;
    }
}

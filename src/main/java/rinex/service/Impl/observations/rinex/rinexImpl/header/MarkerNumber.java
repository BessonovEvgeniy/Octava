package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class MarkerNumber extends AbstractHeaderLabel {

    private String markerNumber;

    public MarkerNumber() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile(".{1,60}MARKER NUMBER");
    }

    @Override
    public Boolean parse(String line)  {
        Matcher matcher = pattern.matcher(line);

        Boolean isFind = matcher.find();
        markerNumber = isFind ? matcher.group().trim() : "";
        return isFind;
    }
}

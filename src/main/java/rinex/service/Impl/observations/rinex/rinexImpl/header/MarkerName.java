package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class MarkerName extends AbstractHeaderLabel {

    private String markerName;

    public MarkerName() {
        init();
    }

    @PostConstruct
    protected void init() {
        pattern = Pattern.compile("(.{1,60})MARKER NAME");
    }

    @Override
    public Boolean parse(String line)  {
        Matcher matcher = pattern.matcher(line);

        Boolean find = matcher.find();
        markerName = find ? matcher.group(1).trim() : "";
        return find;
    }
}

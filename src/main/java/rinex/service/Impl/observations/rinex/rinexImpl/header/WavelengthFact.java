package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public @Data class WavelengthFact extends AbstractHeaderLabel {

    private Integer fullCycle = 0;
    private Integer halfCycle = 0;

    public WavelengthFact() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("(\\d{6})(\\d{6})WAVELENGTH FACT L1/2");
    }

    @Override
    public boolean parse(String line) {

        Matcher matcher = pattern.matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            fullCycle = Integer.parseInt(matcher.group(1).trim());
            halfCycle = Integer.parseInt(matcher.group(2).trim());
        }
        return isFind;
    }
}

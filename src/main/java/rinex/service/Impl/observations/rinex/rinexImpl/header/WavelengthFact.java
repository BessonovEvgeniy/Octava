package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@Service
public @Data class WavelengthFact extends AbstractHeaderLabel {

    private Integer fullCycle;
    private Integer halfCycle;

    public WavelengthFact() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("(\\d{6})(\\d{6})WAVELENGTH FACT L1/2");
    }

    @Override
    public Boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);

        Boolean isFind = matcher.find();
        fullCycle = isFind ? Integer.parseInt(matcher.group(1).trim()) : 0;
        halfCycle = isFind ? Integer.parseInt(matcher.group(2).trim()) : 0;
        return isFind;
    }
}

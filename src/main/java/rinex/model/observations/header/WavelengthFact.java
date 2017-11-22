package rinex.model.observations.header;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("WAVELENGTH FACT L1/2")
public @Data class WavelengthFact implements HeaderLabel {

    private Integer fullCycle;
    private Integer halfCycle;

    private Pattern pattern = Pattern.compile("(\\d{6})(\\d{6})WAVELENGTH FACT L1/2");

    @Override
    public boolean parse(String line) {
        Matcher matcher = pattern.matcher(line);

        Boolean isFind = matcher.find();
        fullCycle = isFind ? Integer.parseInt(matcher.group(1).trim()) : 0;
        halfCycle = isFind ? Integer.parseInt(matcher.group(2).trim()) : 0;
        return isFind;
    }
}

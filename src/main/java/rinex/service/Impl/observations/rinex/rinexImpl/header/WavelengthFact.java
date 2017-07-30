package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class WavelengthFact implements HeaderLabel {

    private Integer fullCycle;
    private Integer halfCycle;

    @Override
    public boolean parse(String line) {

        Matcher matcher = Pattern.compile("(\\d{6})(\\d{6})WAVELENGTH FACT L1/2").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            fullCycle = Integer.parseInt(matcher.group(1).trim());
            halfCycle = Integer.parseInt(matcher.group(2).trim());
        } else {
            fullCycle = 0;
            halfCycle = 0;
        }
        return isFind;
    }
}

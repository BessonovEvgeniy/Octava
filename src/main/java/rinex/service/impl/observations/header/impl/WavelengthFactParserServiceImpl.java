package rinex.service.impl.observations.header.impl;

import com.google.common.primitives.Ints;
import lombok.Data;
import org.springframework.stereotype.Service;
import rinex.model.observations.header.impl.WavelengthFact;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("WAVELENGTH FACT L1/2")
public @Data class WavelengthFactParserServiceImpl implements HeaderLabelParserService <WavelengthFact> {

    private Pattern pattern = Pattern.compile("(\\d{6})(\\d{6})WAVELENGTH FACT L1/2");

    @Override
    public WavelengthFact parse(String line) {
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {

            int fullCycle = Ints.tryParse(matcher.group(1));
            int halfCycle = Ints.tryParse(matcher.group(2));
            Collection<Integer> sats = new ArrayList<>();

            return new WavelengthFact(fullCycle, halfCycle, sats);
        }
        return WavelengthFact.NULL;
    }
}

package ppa.service.impl.observations.header.impl;

import com.google.common.primitives.Ints;
import lombok.Data;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.WaveLengthFact;
import ppa.service.impl.observations.header.HeaderLabelParserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("WAVELENGTH FACT L1/2")
public @Data class WavelengthFactParserServiceImpl implements HeaderLabelParserService <WaveLengthFact> {

    private Pattern pattern = Pattern.compile("([\\d,\\s]{6})([\\d,\\s]{6})([\\d,\\s]{6})(.*)WAVELENGTH FACT L1/2");

    @Override
    public WaveLengthFact parse(String line) {
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {

            int freqL1Param = Ints.tryParse(matcher.group(1).trim());
            int freqL2Param = NumberUtils.toInt(matcher.group(2).trim(), WaveLengthFact.Ambiguities.NoFreq.ordinal());
            int svNum       = NumberUtils.toInt(matcher.group(3).trim(), 0);
            String rawSvs   = matcher.group(4).trim();

            List<String> sats = new ArrayList<>(Arrays.asList(rawSvs.split("\\s\\s\\s")));
            sats.remove("");

            if (sats.size() != svNum) {
                throw new IllegalStateException("Decalred " + svNum + " sv, but found " + sats.size());
            }

            sats = sats.stream().
                    map(sat -> sat.trim().replaceAll("\\s", "0")).
                    collect(Collectors.toList());

            return new WaveLengthFact(freqL1Param, freqL2Param, sats);
        }
        return WaveLengthFact.NULL;
    }
}

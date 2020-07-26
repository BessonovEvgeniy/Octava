package octava.service.impl.observations.header;

import com.google.common.primitives.Ints;
import octava.model.observation.header.impl.WaveLengthFactorModel;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service("WAVELENGTH FACT L1/2")
public class WavelengthFactParserServiceImpl implements HeaderLabelParserService<WaveLengthFactorModel> {

    public final static Pattern PATTERN = Pattern.compile("([\\d,\\s]{6})([\\d,\\s]{6})([\\d,\\s]{6})(.*)WAVELENGTH FACT L1/2");

    @Override
    public WaveLengthFactorModel parse(final String line) {
        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {

            final int freqL1Param = Ints.tryParse(matcher.group(1).trim());
            final int freqL2Param = NumberUtils.toInt(matcher.group(2).trim(), WaveLengthFactorModel.Ambiguities.NoFreq.ordinal());
            final int svNum       = NumberUtils.toInt(matcher.group(3).trim(), 0);
            final String rawSvs   = matcher.group(4).trim();

            List<String> sats = new ArrayList<>(Arrays.asList(rawSvs.split("\\s\\s\\s")));
            sats.remove(EMPTY);

            if (sats.size() != svNum) {
                throw new IllegalStateException("Declared " + svNum + " sv, but found " + sats.size());
            }

            sats = sats.stream().
                    map(sat -> sat.trim().replaceAll("\\s", "0")).
                    collect(Collectors.toList());

            return new WaveLengthFactorModel(freqL1Param, freqL2Param, sats);
        }
        return WaveLengthFactorModel.NULL;
    }
}

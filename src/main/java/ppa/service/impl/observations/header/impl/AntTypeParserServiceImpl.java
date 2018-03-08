package ppa.service.impl.observations.header.impl;

import com.google.common.base.Strings;
import lombok.Data;
import org.springframework.stereotype.Service;
import ppa.model.observation.header.impl.AntType;
import ppa.service.impl.observations.header.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ANT # / TYPE")
public @Data class AntTypeParserServiceImpl implements HeaderLabelParserService<AntType> {

    public final static Pattern PATTERN = Pattern.compile(Strings.repeat("(.{20})", 3) +
            "ANT # / TYPE        ");

    @Override
    public AntType parse(String line) {
        Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            String antennaNumber = matcher.group(1);
            String antennaType = matcher.group(2);
            return new AntType(antennaNumber, antennaType);
        }
        return AntType.NULL;
    }
}

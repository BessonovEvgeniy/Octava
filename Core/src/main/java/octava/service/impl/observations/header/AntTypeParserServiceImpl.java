package octava.service.impl.observations.header;

import com.google.common.base.Strings;
import lombok.Data;
import octava.model.observation.header.impl.AntTypeModel;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ANT # / TYPE")
public @Data class AntTypeParserServiceImpl implements HeaderLabelParserService<AntTypeModel> {

    public final static Pattern PATTERN = Pattern.compile(Strings.repeat("(.{20})", 3) +
            "ANT # / TYPE        ");

    @Override
    public AntTypeModel parse(final String line) {
        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final String antennaNumber = matcher.group(1);
            final String antennaType = matcher.group(2);
            return new AntTypeModel(antennaNumber, antennaType);
        }
        return AntTypeModel.NULL;
    }
}

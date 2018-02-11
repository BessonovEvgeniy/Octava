package rinex.service.impl.observations.header.impl;

import com.google.common.primitives.Ints;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import rinex.exception.UnknownHeaderLabelException;
import rinex.model.observation.header.impl.ObsType;
import rinex.model.observation.header.impl.TypesOfObs;
import rinex.service.impl.observations.header.HeaderLabelParserService;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("# / TYPES OF OBSERV")
public @Data class TypesOfObsParserServiceImpl implements HeaderLabelParserService<TypesOfObs> {

    private Pattern pattern = Pattern.compile("\\s{4,6}(\\d{1,2})(.*)# / TYPES OF OBSERV");

    @Override
    public TypesOfObs parse(String line) {

        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            int numTypes = Ints.tryParse(matcher.group(1));
            String[] obsTypesString =
                    matcher.group(2).trim().replaceAll("\\s{2,5}", " ").split(" ");

            if (numTypes != obsTypesString.length) {
                throw new UnknownHeaderLabelException("Different number observation type. Expected: " +
                        numTypes + " Found: " + obsTypesString.length);
            }

            return new TypesOfObs(Arrays.stream(obsTypesString)
                    .filter(str -> (StringUtils.isNotEmpty(str)))
                    .map(ObsType::getTypeByName)
                    .collect(Collectors.toList()));
        }
        return TypesOfObs.NULL;
    }
}

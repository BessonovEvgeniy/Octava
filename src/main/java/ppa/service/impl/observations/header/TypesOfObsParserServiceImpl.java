package ppa.service.impl.observations.header;

import com.google.common.primitives.Ints;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import ppa.exception.UnknownHeaderLabelException;
import ppa.model.observation.header.impl.ObsType;
import ppa.model.observation.header.impl.TypesOfObs;
import ppa.service.HeaderLabelParserService;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("# / TYPES OF OBSERV")
public class TypesOfObsParserServiceImpl implements HeaderLabelParserService<TypesOfObs> {

    public final static Pattern PATTERN = Pattern.compile("\\s{4,6}(\\d{1,2})(.*)# / TYPES OF OBSERV");

    @Override
    public TypesOfObs parse(String line) {

        Matcher matcher = PATTERN.matcher(line);

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

package octava.service.impl.observations.header;

import com.google.common.primitives.Ints;
import octava.exception.UnknownHeaderLabelException;
import octava.model.observation.header.impl.ObsType;
import octava.model.observation.header.impl.TypesOfObsModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import octava.service.HeaderLabelParserService;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("# / TYPES OF OBSERV")
public class TypesOfObsParserServiceImpl implements HeaderLabelParserService<TypesOfObsModel> {

    public final static Pattern PATTERN = Pattern.compile("\\s{4,6}(\\d{1,2})(.*)# / TYPES OF OBSERV");

    @Override
    public TypesOfObsModel parse(final String line) {

        final Matcher matcher = PATTERN.matcher(line);

        if (matcher.find()) {
            final int numTypes = Ints.tryParse(matcher.group(1));
            final String[] obsTypesString =
                    matcher.group(2).trim().replaceAll("\\s{2,5}", " ").split(" ");

            if (numTypes != obsTypesString.length) {
                throw new UnknownHeaderLabelException("Different number observation type. Expected: " +
                        numTypes + " Found: " + obsTypesString.length);
            }

            return new TypesOfObsModel(Arrays.stream(obsTypesString)
                    .filter(str -> (StringUtils.isNotEmpty(str)))
                    .map(ObsType::getTypeByName)
                    .collect(Collectors.toList()));
        }
        return TypesOfObsModel.NULL;
    }
}

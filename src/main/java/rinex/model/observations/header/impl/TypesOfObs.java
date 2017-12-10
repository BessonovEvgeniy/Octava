package rinex.model.observations.header.impl;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import rinex.exception.UnknownHeaderLabelException;
import rinex.model.observations.header.HeaderLabel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("# / TYPES OF OBSERV")
public @Data class TypesOfObs implements HeaderLabel {

    private List<Type> obsTypes = new ArrayList<>();

    private Pattern pattern = Pattern.compile("\\s{4,6}(\\d{1,2})(.*)# / TYPES OF OBSERV");

    @Override
    public boolean parse(String line) {

        Matcher matcher = pattern.matcher(line);

        Boolean isFind = matcher.find();
        Integer numTypes = isFind ? Integer.parseInt(matcher.group(1)) : 0;
        String[] obsTypesString = isFind ?
                matcher.group(2).trim().replaceAll("\\s{2,5}", " ").split(" ") : new String[0];

        if (isFind && numTypes != obsTypesString.length) {
            throw new UnknownHeaderLabelException("Different number observation type. Expected: " + numTypes + " Found: " + obsTypes.size());
        } else if (isFind) {
            obsTypes.addAll(Arrays.stream(obsTypesString)
                    .filter(str -> (StringUtils.isNotEmpty(str)))
                    .map(Type::getTypeByName)
                    .collect(Collectors.toList()));
        }
        return isFind;
    }

    public enum Type {
        L1("G"), L2("G"), L5("G"), C1("G"), P2("G"), G1("R"), G2("R");

        private final String system;

        Type(String systemCode) {
            system = systemCode;
        }

        public String getSystem() {
            return system;
        }

        public boolean isSystemRequired(String satName) {
            return  satName.contains(system);
        }

        static Type getTypeByName(String name) {
            for (Type type : Type.values()) {
                if (name.equals(type.name())) {
                    return type;
                }
            }
            return null;
        }
    }
}

package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;
import rinex.model.rinex.Observations;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public @Data class TypesOfObs extends AbstractHeaderLabel {

    private List<Type> obsTypes = new ArrayList<>();

    public enum Type {
        L1("G"), L2("G"), L5("G"), C1("G"), P2("G"), G1("R"), G2("R");

        String system;

        Type(String systemCode) {
            system = systemCode;
        }

        public String getSystem() {
            return system;
        }

        public boolean isSystemRequired(String satName) {
            return  satName.contains(system);
        }

        public static Type getTypeByName(String name) {
            for (Type type : Type.values()) {
                if (name.equals(type.name())) {
                    return type;
                }
            }
            return null;
        }

        public int getOrdinal(List<Type> readedTypes) {
            int ordinal = -1;
            for (int i = 0; i < readedTypes.size(); i++) {
                if (readedTypes.get(i).equals(this)) {
                    ordinal = i;
                    break;
                }
            }
            return ordinal;
        }
    }

    public TypesOfObs() {
        init();
    }

    @PostConstruct
    private void init() {
        pattern = Pattern.compile("\\s{4,6}(\\d{1,2})(.*)# / TYPES OF OBSERV");
    }

    @Override
    public Boolean parse(String line) throws RinexHeaderException {

        Matcher matcher = pattern.matcher(line);

        Boolean isFind = matcher.find();
        Integer numTypes = isFind ? Integer.parseInt(matcher.group(1)) : 0;
        String[] obsTypesString = isFind ?
                matcher.group(2).trim().replaceAll("\\s{2,5}", " ").split(" ") : new String[0];

        if (isFind && numTypes != obsTypesString.length) {
            throw new RinexHeaderException("Different number observation type. Expected: " + numTypes + " Found: " + obsTypes.size());
        } else if (isFind) {
            obsTypes.addAll(Arrays.stream(obsTypesString)
                    .filter(str -> (str != null && !str.isEmpty()))
                    .map(str -> Type.getTypeByName(str))
                    .collect(Collectors.toList()));
        }
        return isFind;
    }
}

package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public @Data class TypesOfObserv extends AbstractHeaderLabel {

    private List<String> obsTypes = new ArrayList<>();

    public TypesOfObserv() {
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
            obsTypes.addAll(Arrays.stream(obsTypesString).
                    filter(str -> (str != null && !str.isEmpty())).collect(Collectors.toList()));
        }
        return isFind;
    }
}

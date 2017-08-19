package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import rinex.service.HeaderLabel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public @Data class TypesOfObserv implements HeaderLabel {

    private List<String> obsTypes = new ArrayList<>();

    @Override
    public boolean parse(String line) throws RinexHeaderException {

        Matcher matcher = Pattern.compile("(\\s{4,5})(\\d{4})(\\*)# / TYPES OF OBSERV").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            int numTypes = Integer.parseInt(matcher.group(2));

            String[] obsTypesString = matcher.group(3).trim().split(" ");

            if (numTypes != obsTypes.size()) {
                throw new RinexHeaderException("Different number observation type. Expected: " + numTypes + " Found: " + obsTypes.size());
            } else {
                obsTypes.addAll(Arrays.stream(obsTypesString).
                        filter(str -> (str != null && !str.isEmpty())).collect(Collectors.toList()));
            }
        } else {
            obsTypes = new ArrayList<>();
        }
        return isFind;
    }
}

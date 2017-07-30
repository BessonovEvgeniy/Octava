package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import rinex.service.HeaderLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class TypesOfObserv implements HeaderLabel {

    private List<String> obsTypes = new ArrayList<>();

    @Override
    public boolean parse(String line) throws RinexHeaderException {

        Matcher matcher = Pattern.compile("(\\d{6})(\\d{6})WAVELENGTH FACT L1/2").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            int numTypes = Integer.parseInt(matcher.group(1));

            for (int i = 2; i <= numTypes; i++) {
                if (matcher.find(i)) {
                    obsTypes.add(matcher.group(i));
                }
            }
            if (numTypes != obsTypes.size()) {
                throw new RinexHeaderException("Different number observation type. Expected: " + numTypes + " Found: " + obsTypes.size());
            }
        } else {
            obsTypes = new ArrayList<>();
        }
        return isFind;
    }
}

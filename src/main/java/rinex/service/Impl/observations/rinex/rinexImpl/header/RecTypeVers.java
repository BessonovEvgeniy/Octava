package rinex.service.Impl.observations.rinex.rinexImpl.header;

import lombok.Data;
import rinex.service.HeaderLabel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public @Data class RecTypeVers implements HeaderLabel {

    private String rec;
    private String type;
    private String vers;

    @Override
    public boolean parse(String line) {

        Matcher matcher = Pattern.compile("(.{20})(.{20})(.{20})(REC # / TYPE / VERS)").matcher(line);

        boolean isFind = matcher.find();
        if (isFind) {
            rec = matcher.group(1).trim();
            type = matcher.group(2).trim();
            vers = matcher.group(3).trim();
        } else {
            rec = null;
            type = null;
            vers = null;
        }
        return isFind;
    }
}
